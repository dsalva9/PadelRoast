<div align="center">
  <h1>🔥 PadelRoast</h1>
  <p><strong>Turn every padel match into content worth sharing.</strong></p>
</div>

---

PadelRoast is a sports-entertainment mobile application built for amateur padel players. It processes post-match scores, calculates Elo adjustments, identifies game dynamics, and generates shareable comedy "Roast Cards" with highlights and copy-paste-ready WhatsApp messages.

---

## 📂 Project Structure

- **`/app`**: Native Android client application built with Kotlin, Jetpack Compose, and Room.
- **`/supabase`**: Backend infrastructure using Supabase Edge Functions written in TypeScript/Deno.
- **`/docs`**: Comprehensive documentation, including:
  - 🏗️ [Architecture Overview](file:///c:/Users/dsalv/Projects/padel-app/docs/ARCHITECTURE_OVERVIEW.md) — System flow, local fallback engine, and database schema mappings.
  - ⚡ [Supabase Runbook](file:///c:/Users/dsalv/Projects/padel-app/docs/SUPABASE_RUNBOOK.md) — Local Edge Function development, secrets setup, and deployment.
  - 🧠 [AI Banter Playbook](file:///c:/Users/dsalv/Projects/padel-app/docs/AI_BANTER_PLAYBOOK.md) — Prompt design, temperature parameter configs, and subscription tier model routing.
  - 🤖 [Play Store Release Guide](file:///c:/Users/dsalv/Projects/padel-app/docs/PLAY_STORE_RELEASE_GUIDE.md) — Building production Android App Bundles, signing keys, and Proguard rules.
  - 🍏 [iOS Publishing Guide](file:///c:/Users/dsalv/Projects/padel-app/docs/IOS_PUBLISHING_GUIDE.md) — App Store submission and Xcode configuration.
  - 📝 [Product Requirements Document (PRD)](file:///c:/Users/dsalv/Projects/padel-app/docs/PRD.md) — Original product spec and design principles.

---

## 🛠️ Local Development Setup

### 1. Android Client Setup
1. Download and open **Android Studio**.
2. Select **Open** and select this directory.
3. Create a `.env` file in the root directory and define the Supabase credentials:
   ```env
   # API Keys
   SUPABASE_URL=your_supabase_project_url
   SUPABASE_ANON_KEY=your_supabase_anon_key
   ```
4. Sync Gradle, select an emulator or physical device, and press **Run**.

### 2. Supabase Backend Setup
To run the local Edge Functions backend:
1. Ensure Docker is running.
2. Install the Supabase CLI.
3. Start the Supabase local stack:
   ```bash
   supabase start
   ```
4. Run the function locally with env variables configured:
   ```bash
   supabase functions serve generate-roast --no-verify-jwt --env-file supabase/functions/generate-roast/.env
   ```

Refer to the [Supabase Runbook](file:///c:/Users/dsalv/Projects/padel-app/docs/SUPABASE_RUNBOOK.md) for detailed deployment commands.
