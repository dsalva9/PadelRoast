# Supabase Edge Functions Runbook

This runbook outlines how to develop, test, and deploy the Supabase Edge Functions for PadelRoast. The central function is `generate-roast` which communicates with the OpenAI API.

---

## 1. Local Development Setup

### Prerequisites
1. Install **Docker Desktop** (required to run the Supabase local container stack).
2. Install the **Supabase CLI**:
   - **macOS/Linux:** `brew install supabase/tap/supabase`
   - **Windows (PowerShell):** `scoop bucket add supabase https://github.com/supabase/scoop-bucket.git; scoop install supabase` or install via direct binary.
3. Install **Deno** (optional, for IDE autocompletion and linting of Deno Edge Functions).

### Running Supabase Locally
Navigate to the root directory of the project and execute:

```bash
# Initialize Supabase configuration (if not already done)
supabase init

# Start local Supabase containers (Database, Auth, Studio, etc.)
supabase start
```

Once started, the CLI will output the local API URL, JWT secret, and database connection details.

---

## 2. Testing Edge Functions Locally

To test the `generate-roast` function locally:

### Configure Local Environment Variables
Create a file named `supabase/functions/generate-roast/.env` or set environment variables in your local shell:

```env
OPENAI_API_KEY=your_openai_api_key_here
```

### Serve the Function Locally
Run the following command to spin up the local Deno server. The `--no-verify-jwt` flag allows you to test the HTTP request directly without supplying Supabase user authorization headers.

```bash
supabase functions serve generate-roast --no-verify-jwt --env-file supabase/functions/generate-roast/.env
```

The function will be served at `http://localhost:54321/functions/v1/generate-roast`.

### Run a Test Request
You can curl the endpoint using a sample JSON payload:

```bash
curl -X POST http://localhost:54321/functions/v1/generate-roast \
  -H "Content-Type: application/json" \
  -d '{
    "players": ["Alice", "Bob", "Charlie", "Dave"],
    "scores": [[6, 2], [6, 4]],
    "tone": "balanced",
    "trigger": "Dominant",
    "tier": "free",
    "locale": "en"
  }'
```

---

## 3. Managing Secrets & Deployment

### Linking Your Project
Link your local environment to the remote Supabase project:

```bash
supabase login
supabase link --project-ref your-supabase-project-ref
```

### Setting Remote Environment Secrets
You must configure the OpenAI API key on the remote Supabase production environment so the deployed function can call OpenAI:

```bash
supabase secrets set OPENAI_API_KEY=sk-proj-XXXXXX...
```

To list existing secrets:
```bash
supabase secrets list
```

### Deploys
Deploy your local function to production:

```bash
supabase functions deploy generate-roast
```
This will compile and publish the function to your remote project ref. The function will be accessible at:
`https://<your-project-ref>.supabase.co/functions/v1/generate-roast`
