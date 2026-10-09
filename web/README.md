# PadelRoast Web Landing Page

The official marketing and pre-launch web landing page for **PadelRoast**, designed for high conversions, sports-entertainment engagement, and direct Android beta distribution.

---

## 🚀 Quick Start (Local Development)

```bash
cd web
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

---

## 📱 Features

- **Interactive Animated Phone Mockup:** 4-step auto-playing smartphone demo (Match Score → Tone Selector → Roast Card → WhatsApp Share) inspired by CambioCromos.
- **Direct APK Beta Download:** Instant 1-tap download of `padelroast-beta.apk` without waiting for Google Play approval.
- **Store Badges:** Google Play (In Review status) and Apple iOS (Coming Soon status).
- **Tone Showcase:** Interactive preview of all 4 comedy levels (*Casual*, *Sports Desk*, *Savage*, *Savage!!*).
- **Ghost Profiles Feature:** Explaining why only ONE player needs the app.
- **Roast Card Deck:** Real hilarious padel comedy cards with 1-click WhatsApp banter copy.
- **Multi-Sport Franchise Ready:** Extensible `sportConfig.ts` to launch `tennisroast.com`, `soccerroast.com`, etc. in minutes.

---

## 🌐 Deploying to Vercel (Preview & Live)

### Step 1: Connect to Vercel
1. Go to [vercel.com/dashboard](https://vercel.com/dashboard).
2. Click **Add New...** → **Project**.
3. Import your GitHub repository (`padel-app`).

### Step 2: Configure Project Settings
- **Framework Preset:** Next.js (automatically detected)
- **Root Directory:** Click `Edit` and select **`web`** ⚠️ *(Crucial!)*
- **Build Command:** `next build` (default)
- **Output Directory:** `.next` (default)
- **Install Command:** `npm install` (default)

### Step 3: Deploy & Test
Click **Deploy**. Within 60 seconds, Vercel will give you a live URL like:
`https://padel-app-web.vercel.app` (or custom name like `https://padelroast.vercel.app`).

You can share this preview link on your phone, test the interactive phone animation, and try downloading the APK!

---

## 🔗 Connecting `padelroast.com` (When Ready)

When you are ready to point your actual domain to Vercel:
1. In Vercel Project Settings → **Domains**, add `padelroast.com` and `www.padelroast.com`.
2. In your DNS registrar (where you bought `padelroast.com`), add:
   - **A Record:** `@` points to `76.76.21.21` (Vercel IP)
   - **CNAME Record:** `www` points to `cname.vercel-dns.com`

### Redirecting `.app` to `.com`
In your domain registrar for `padelroast.app`:
- Set up a **Domain Forward / URL Redirect**:
  - Source: `https://padelroast.app`
  - Destination: `https://padelroast.com`
  - Redirect Type: **301 (Permanent)**
