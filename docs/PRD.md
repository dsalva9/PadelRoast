# Product Requirements Document — PadelRoast

> *"Turn every match into content worth sharing."*

| Field   | Value       |
|---------|-------------|
| Version | 1.2         |
| Date    | 2026-03-30  |
| Status  | Build-Ready |

---

## 1. Vision & Problem

### The Problem

Amateur padel players finish matches, share results in WhatsApp groups, and have **no tools** to turn those moments into engaging content.

Existing apps focus on:

- **Logistics** — Playtomic
- **Stats** — PadelLog
- **Social coordination**

> 👉 **None focus on entertainment + shareability**

Current behavior:

- Screenshot score → drop in WhatsApp → short-lived reactions
- No persistence, no identity, no narrative

### The Vision

**PadelRoast is a padel entertainment engine.**

It transforms match results into:

- 🔥 **Roast Cards** (hero output)
- 📝 Match narratives
- 📤 Shareable moments

> ⏱️ Delivered in **10–15 seconds**

### Success Statement

PadelRoast becomes:

> 👉 **The default way players share match results**

Measured by:

- Shares per match
- Click-through rate from shared cards
- Ghost → user conversions

---

## 2. Branding & Naming Strategy

### Primary Brand

> 👉 **PadelRoast**

- **Tone:** bold, humorous, slightly provocative
- **Core promise:** *"this app roasts your matches"*

### Secondary Domain Strategy

> 👉 **PadelBanter.com**

- Redirects to PadelRoast.com
- Captures alternative / mis-typed traffic
- Reserved for:
  - SEO content
  - Blog / softer tone content (future)

### Brand Principle

- The brand promises **intensity** ("Roast")
- The product delivers **adjustable tone** (Casual → Savage)

---

## 3. Target Users

### Primary Persona: "Group Chat MVP"

- 25–45 years old
- Plays 1–4× per week
- Active in WhatsApp groups
- Enjoys memes, trash talk, social competition

### Secondary Persona: "Rivalry Keeper"

- Plays with same group regularly
- Tracks informal results
- Motivated by status and history

### Anti-Persona

> ❌ Performance optimizers (stats-heavy users)

---

## 4. Core User Flow (MVP)

> **Constraint:** 10–15 seconds

```
LOG MATCH → GENERATE → SHARE
```

### 4.1 Match Input (5–8 sec)

**Inputs:**

- Player names (auto-suggested after first use)
- Score (tap-based input)
- *Optional:*
  - Match label
  - Context ("comeback", "choke")

**Speed Rules:**

- No required fields beyond essentials
- No blocking modals
- "Same Again" pre-fills last players

### 4.2 AI Generation (Instant)

#### Hero Output

> 👉 **Roast Card** (always first)

Also generates:

- 1–2 alternative cards (swipeable)
- Short recap (internal / optional)

#### Content Selection UX

- 1 primary card
- 1–2 swipe alternatives
- No decision friction

#### Content Quality Bar

If output is:

- ❌ Generic
- ❌ Low-energy
- ❌ Repetitive

> 👉 System must:
> - Auto-regenerate once
> - Fallback to stronger variation

### 4.3 Share (1 tap)

- WhatsApp (primary)
- Instagram Stories
- Save image
- Copy link

#### First-Time Nudge

Shown once:

> *"This is what your group will see — drop it in WhatsApp 👇"*

### 4.4 Organizer Setup Flow (First-Time Experience)

**Objective:** Allow one user (Organizer) to set up and share matches instantly—even if others are not users.

#### Flow

| Step | Action | Details |
|------|--------|---------|
| 1 | Open App | No mandatory signup. Lands directly on "Log Match" |
| 2 | Add Players | Type names OR reuse suggestions |
| 3 | Input Match | Enter score + optional context |
| 4 | Generate & Share | Roast Card generated → shared to WhatsApp |

#### Ghost Profiles (Step 2)

- Players created **without accounts**
- Name-only identity
- No invites required

#### Success Condition

> Only **ONE person** needs the app for it to work

### 4.5 Ghost Profiles & Claim System

**Objective:** Convert non-users into users via ego-driven discovery.

#### Ghost Profiles

- Represent players without accounts
- Store:
  - Name
  - Match history
  - Titles (if applicable)

#### Claim Entry Point

User clicks shared link → **Spectator Mode** shows:

- Roast Card
- Context line
- Player name (if included)

**Claim CTA:**

- *"Claim your stats"*
- *"That you? See your record"*

#### Claim Flow

1. Tap CTA
2. Open/install app
3. Select identity: *"I am [Name]"*

#### Merge Logic

Ghost → User profile transfers:

- ✅ History
- ✅ Titles
- ✅ Stats

#### Ambiguity Handling

- Multiple matches → user selects correct one

#### Constraints (MVP)

- No strict verification
- Trust-based identity

### 4.6 Organizer Value Proposition

**Why they install first** — the organizer:

- Logs results
- Shares results
- Drives the group

| Value Type    | Benefits                              |
|---------------|---------------------------------------|
| Immediate     | Fast logging, instant shareable content |
| Ongoing       | Tracks matches, builds group narrative  |
| Social Status | Becomes *"the one who posts results"*   |

---

## 5. AI Content Engine

### 5.1 Hybrid System

#### Deterministic Layer

Triggers:

- 🎯 Upset
- 💥 Blowout
- ⚔️ Close match
- 🔄 Comeback

#### Generative Layer

AI generates:

- Phrasing
- Humor
- Variations

**Inputs:** names, score, trigger, tone

### 5.2 Anti-Repetition System

- **20–30+ variations** per trigger × tone
- Variation across:
  - Structure
  - Vocabulary
  - Punchline

**Repetition Avoidance:**

- Track recent outputs
- Avoid structural similarity

### 5.3 Tone System

| Level    | Description          |
|----------|----------------------|
| Casual   | Light, friendly      |
| Balanced | Playful, competitive |
| Savage   | No mercy             |

### 5.4 Titles (Identity Layer)

Examples:

- 👑 *King Slayer*
- 📊 *Stat Padder*
- 👻 *The Ghost*

Used in: cards, generated text

### 5.5 Card Design

| Format     | Use Case         |
|------------|------------------|
| 1080×1080  | Feed / WhatsApp  |
| 1080×1920  | Stories / Reels   |

**Includes:**

- Names
- Score
- Roast text
- Titles
- Branding + link

---

## 6. Viral Loop & Sharing

### Card-Level CTA

- *"Check your stats"*
- *"Claim your title"*

### Deep Linking

- Opens web landing (Next.js)

### Spectator Mode

Shows:

- Card
- Context
- **No login required**

### Goal

> 👉 **Convert viewers → users**

---

## 7. Group Continuity (Circles — P1)

- Matches grouped by recurring players
- Enables:
  - 🏆 Rivalries
  - 🔗 Continuity
  - 🪪 Identity

---

## 8. Post-Match Engagement

On next open:

- 📈 Ranking changes
- 🔥 Streaks
- 🏅 New titles

---

## 9. Monetization

| Tier    | Features                                  |
|---------|-------------------------------------------|
| Free    | Limited cards/month, basic theme           |
| Premium | Unlimited cards, premium themes, better AI |

---

## 10. Technical Architecture

### App

> 👉 **Flutter**

- iOS + Android
- Native sharing
- Widget-based card rendering

### Web

> 👉 **Next.js**

- SEO landing page
- Spectator mode
- Install funnel

### Backend (Week 4+)

> 👉 **Firebase**

- Firestore
- Hosting
- Dynamic Links

### Local Storage

> 👉 **Hive** (Flutter)

- Players
- Matches
- Repetition tracking

### AI Layer

- **Default:** mid-tier model
- **Fallback:** high-tier model

### Image Generation

- Flutter widgets → image export

### Sharing

- Native device share

---

## 11. Development Strategy

| Phase              | Timeline    | Focus                                    |
|--------------------|-------------|------------------------------------------|
| Phase 1            | Weeks 1–2   | Local only — card generation + sharing    |
| Phase 2            | Weeks 3–4   | Improve content quality + web landing     |
| Phase 3            | Weeks 4–6   | Firebase, persistence, monetization       |

---

## 12. Product Principles

1. **Speed > Everything**
2. **Content Quality = Product Quality**
3. **Roast Card is the product**
4. **No friction before sharing**
5. **Ego drives engagement**
6. **Avoid feature creep**

---

## 🧨 Final Note

This is **not:**

- ❌ A stats app
- ❌ A booking tool
- ❌ A social network

> 👉 **This is a content engine that turns matches into moments people want to share.**