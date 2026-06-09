# iOS App Store Publishing Guide (Without a Mac)

> For PadelRoast — Flutter app targeting iOS + Android

---

## TL;DR

You **can** build and publish a Flutter app to the App Store without owning a Mac, but you need:

1. An **Apple device** (iPhone or iPad) — for enrollment and 2FA
2. An **Apple Developer account** ($99/year)
3. A **cloud CI/CD service** (Codemagic) — to build, sign, and upload

```
Your Windows PC ──push──▶ GitHub ──trigger──▶ Codemagic (macOS VM)
                                                 │
                                        builds Flutter iOS
                                        signs with your certs
                                        uploads to TestFlight
                                                 │
                                                 ▼
                                         App Store Connect
```

---

## Phase 1: Prerequisites (Do This Now)

### 1.1 Get an Apple Device

> [!IMPORTANT]
> You **cannot** complete Apple Developer enrollment on Windows alone. You need an iPhone, iPad, or Mac for identity verification and 2FA.

**Cheapest options:**
| Option | Cost | Notes |
|--------|------|-------|
| Use an existing iPhone/iPad | Free | Any relatively recent model works |
| Buy used iPhone SE | ~€80–120 | Only needed for enrollment + 2FA |
| Borrow one for 30 min | Free | Enrollment takes ~15 min |

You only need the Apple device for:
- Creating your Apple ID with 2FA
- Downloading the **Apple Developer** app
- Completing identity verification (photo ID scan)
- Occasional 2FA codes (can also use SMS after setup)

### 1.2 Create Apple ID

If you don't already have one:

1. Go to [appleid.apple.com](https://appleid.apple.com)
2. Create account with your email
3. **Enable Two-Factor Authentication** (required)
   - This step needs an Apple device

### 1.3 Enroll in Apple Developer Program

| Field | Details |
|-------|---------|
| Cost | **$99 USD/year** |
| Time | ~48 hours for approval |
| URL | [developer.apple.com/programs/enroll](https://developer.apple.com/programs/enroll) |

**Steps:**

1. Download the **Apple Developer** app on your iPhone/iPad
2. Sign in with your Apple ID
3. Start enrollment
4. Choose **Individual** (not Organization — you don't need a D-U-N-S number)
5. Scan your government-issued photo ID (passport or national ID)
6. Pay $99
7. Wait for approval (typically 24–48 hours)

> [!TIP]
> Start this process **early** (Sprint 0). Approval can take up to 48 hours, and you'll need it before setting up code signing.

---

## Phase 2: Set Up App Store Connect (Week 1–2)

Once approved, you can do everything below **from your Windows browser**.

### 2.1 Create Your App in App Store Connect

1. Go to [appstoreconnect.apple.com](https://appstoreconnect.apple.com)
2. Click **My Apps** → **+** → **New App**
3. Fill in:

| Field | Value |
|-------|-------|
| Platform | iOS |
| Name | PadelRoast |
| Primary Language | English (or Spanish) |
| Bundle ID | `com.padelroast.app` (register this first — see below) |
| SKU | `padelroast-ios` |

### 2.2 Register Bundle ID

1. Go to [developer.apple.com/account/resources/identifiers](https://developer.apple.com/account/resources/identifiers/list)
2. Click **+** → **App IDs** → **App**
3. Set:
   - Description: `PadelRoast`
   - Bundle ID (Explicit): `com.padelroast.app`
4. Enable capabilities you need:
   - ✅ Push Notifications (if planned)
   - ✅ Associated Domains (for deep links)
   - ✅ In-App Purchase (for premium tier)

### 2.3 Create App Store Connect API Key

> This is how Codemagic authenticates with Apple on your behalf — **no Mac needed**.

1. Go to [appstoreconnect.apple.com/access/integrations/api](https://appstoreconnect.apple.com/access/integrations/api)
2. Click **+** to generate a new key
3. Set:
   - Name: `Codemagic CI`
   - Access: **App Manager**
4. Download the `.p8` key file
5. Note down:
   - **Key ID**
   - **Issuer ID** (shown at the top of the page)

> [!CAUTION]
> You can only download the `.p8` file **once**. Save it securely. You'll upload it to Codemagic.

---

## Phase 3: Set Up Codemagic CI/CD (Sprint 0)

### 3.1 Why Codemagic

| Feature | Details |
|---------|---------|
| Free tier | **500 min/month** on macOS M2 VMs |
| Flutter-native | Built specifically for Flutter projects |
| Auto code signing | Generates certificates and provisioning profiles automatically |
| Direct upload | Pushes builds straight to TestFlight |
| No Mac required | Everything runs on their cloud macOS machines |

> 500 free minutes is plenty for an indie project — a Flutter iOS build takes ~10–15 minutes.

### 3.2 Setup Steps

1. Go to [codemagic.io](https://codemagic.io) and sign up (use GitHub)
2. Connect your GitHub repository
3. Select your Flutter project
4. Go to **Settings** → **Distribution** → **iOS code signing**
5. Choose **Automatic** code signing
6. Add your App Store Connect API key:
   - Upload the `.p8` file
   - Enter Key ID
   - Enter Issuer ID
7. Codemagic will **automatically**:
   - Generate a distribution certificate
   - Create a provisioning profile
   - Sign your `.ipa`
   - Upload to TestFlight

### 3.3 codemagic.yaml Configuration

Create this file in your Flutter project root:

```yaml
workflows:
  ios-release:
    name: iOS Release
    max_build_duration: 30
    instance_type: mac_mini_m2
    environment:
      flutter: stable
      xcode: latest
      groups:
        - app_store_credentials  # configured in Codemagic UI
      ios_signing:
        distribution_type: app_store
        bundle_identifier: com.padelroast.app
    scripts:
      - name: Get Flutter packages
        script: flutter pub get
      - name: Build iOS
        script: flutter build ipa --release --export-options-plist=/Users/builder/export_options.plist
    artifacts:
      - build/ios/ipa/*.ipa
    publishing:
      app_store_connect:
        auth: integration
        submit_to_testflight: true
        # submit_to_app_store: false  # set true when ready for production

  android-release:
    name: Android Release
    max_build_duration: 30
    instance_type: mac_mini_m2
    environment:
      flutter: stable
      groups:
        - google_play_credentials
    scripts:
      - name: Get Flutter packages
        script: flutter pub get
      - name: Build Android
        script: flutter build appbundle --release
    artifacts:
      - build/app/outputs/bundle/**/*.aab
    publishing:
      google_play:
        credentials: $GCLOUD_SERVICE_ACCOUNT_CREDENTIALS
        track: internal
```

### 3.4 Your Build Workflow

```
1. Write code on Windows
2. Push to GitHub
3. Codemagic auto-triggers (or manual trigger)
4. macOS VM builds → signs → uploads to TestFlight
5. Test on your iPhone via TestFlight app
6. When ready → promote to App Store review
```

---

## Phase 4: Prepare Store Listing (Sprint 5)

Everything below is done in **App Store Connect from your browser**.

### 4.1 Required Metadata

| Item | Details |
|------|---------|
| App Name | `PadelRoast` |
| Subtitle | `Roast your padel matches` (30 chars max) |
| Description | Full app description (4000 chars max) |
| Keywords | `padel, roast, banter, match, share, score, card` (100 chars max) |
| Category | Sports (primary), Entertainment (secondary) |
| Support URL | Your landing page (padelroast.com) |
| Marketing URL | padelroast.com |
| Privacy Policy URL | padelroast.com/privacy |
| Age Rating | 12+ (infrequent mild humor) |

### 4.2 Screenshots

| Device | Size (px) | Required |
|--------|-----------|----------|
| 6.9" iPhone (Pro Max) | 1320 × 2868 | Yes |
| 6.7" iPhone | 1290 × 2796 | Auto-scaled from 6.9" |
| 6.5" iPhone | 1284 × 2778 | Auto-scaled |
| 5.5" iPhone | 1242 × 2208 | Auto-scaled |
| iPad Pro 13" | 2064 × 2752 | Only if you support iPad |

**How to generate screenshots without a Mac:**

1. Use your Android emulator or physical Android device to capture the screens
2. Design marketing frames in Figma/Canva at the required dimensions
3. Or use tools like [screenshots.pro](https://screenshots.pro) or [theapplaunchpad.com](https://theapplaunchpad.com)
4. Minimum **3 screenshots**, recommended **5–8**

**Screenshot content ideas:**

1. Match input screen — *"Log your match in seconds"*
2. Roast Card generated — *"Get your roast card"*
3. Share flow — *"Drop it in WhatsApp"*
4. Multiple card styles — *"Swipe for alternatives"*
5. Tone selector — *"Casual to Savage — you choose"*

### 4.3 App Icon

- **1024×1024px** PNG, no alpha channel, no rounded corners (Apple applies them)
- Must match the icon used in the app

### 4.4 Privacy Policy

You **must** have a publicly accessible privacy policy. Create a page at `padelroast.com/privacy` covering:

- What data you collect (names, scores, AI-generated content)
- How you use it
- Third-party services (Firebase, AI provider)
- Data retention
- Account deletion process (required if you offer accounts)
- Contact information

### 4.5 App Privacy Questionnaire

In App Store Connect → **App Privacy**, you must declare:

| Data Type | Collected? | How Used |
|-----------|------------|----------|
| Name | Yes (player names) | App Functionality |
| Gameplay Content | Yes (match scores) | App Functionality |
| Identifiers | Yes (if Firebase) | Analytics, App Functionality |
| Diagnostics | Yes (Crashlytics) | App Functionality |
| Purchases | Yes (if premium) | App Functionality |

---

## Phase 5: Review & Submission

### 5.1 TestFlight (Internal Testing)

Once Codemagic uploads a build:

1. Open **TestFlight** app on your iPhone
2. Install the build
3. Test all flows on real iOS device
4. Fix any iOS-specific issues
5. Repeat until stable

> [!TIP]
> You can add up to **25 internal testers** (email-based) without app review. Use this to get friends to test.

### 5.2 Submit for Review

1. In App Store Connect, select your app
2. Go to the current version
3. Fill in all metadata (screenshots, description, etc.)
4. Under **Build**, select the TestFlight build to submit
5. **App Review Information:**
   - If the app requires login: provide test credentials
   - For PadelRoast MVP (no auth): note this in review notes
   - Add review notes: *"This app generates AI-powered humorous content about padel match results. It uses locally stored player names (not real accounts) to generate shareable cards."*
6. Click **Submit for Review**

### 5.3 Review Timeline

| Scenario | Timeline |
|----------|----------|
| First submission | 24–48 hours (typically) |
| Subsequent updates | 24 hours |
| If rejected | Fix issues → resubmit → 24 hours |

### 5.4 Common Rejection Reasons to Avoid

| Reason | How to Prevent |
|--------|----------------|
| AI-generated content policy | Clearly state content is AI-generated, add report mechanism |
| Missing privacy policy | Ensure URL is live and accessible |
| Broken links | Test all URLs in the app |
| Login required but no test account | Provide demo credentials or make app work without login |
| Misleading screenshots | Only show actual app UI |
| Incomplete app | Ensure all features shown in screenshots work |
| In-App Purchase issues | Test purchase flow in sandbox |

---

## Cost Summary

| Item | Cost | Frequency |
|------|------|-----------|
| Apple Developer Program | $99 | Annual |
| Codemagic (free tier) | $0 | 500 min/month |
| Codemagic (if exceeded) | ~$0.095/min | Pay-as-you-go |
| Used iPhone (if needed) | ~€80–120 | One-time |
| **Total to get started** | **~$99** | — |

---

## Quick Reference: What Needs a Mac vs. What Doesn't

| Task | Mac Required? | Alternative |
|------|--------------|-------------|
| Enroll in Apple Developer Program | ❌ No | iPhone/iPad works |
| Create App Store Connect listing | ❌ No | Browser on Windows |
| Write Flutter code | ❌ No | VS Code on Windows |
| Build iOS `.ipa` | ✅ Yes | **Codemagic** (cloud Mac) |
| Code sign for App Store | ✅ Yes | **Codemagic** (automatic) |
| Upload to TestFlight | ✅ Yes | **Codemagic** (automatic) |
| Test on iOS Simulator | ✅ Yes | Test on physical iPhone via TestFlight |
| Submit to App Store | ❌ No | Browser on Windows |
| Manage reviews & releases | ❌ No | Browser on Windows |

---

## Action Items for Sprint 0

- [ ] Get access to an iPhone/iPad (borrow or buy)
- [ ] Create Apple ID with 2FA enabled
- [ ] Enroll in Apple Developer Program ($99)
- [ ] Wait for approval (24–48 hours)
- [ ] Register Bundle ID (`com.padelroast.app`)
- [ ] Create App Store Connect API Key (`.p8`)
- [ ] Sign up for Codemagic (free tier)
- [ ] Connect GitHub repo to Codemagic
- [ ] Upload API key to Codemagic
- [ ] Create `codemagic.yaml` in project root
- [ ] Trigger first test build to verify pipeline works
