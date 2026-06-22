import "jsr:@supabase/functions-js/edge-runtime.d.ts";

const CORS_HEADERS = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Methods": "POST, OPTIONS",
  "Access-Control-Allow-Headers":
    "authorization, x-client-info, apikey, content-type",
};

const LOCALE_MAP: Record<string, string> = {
  en: "English",
  es: "Spanish",
  fr: "French",
  it: "Italian",
  nl: "Dutch",
  sv: "Swedish",
};

const TONE_INSTRUCTIONS: Record<string, string> = {
  casual:
    "Use a light, friendly tone with subtle ribbing. Keep it fun and easygoing — like teasing a friend after a casual match.",
  balanced:
    "Be clever, funny, and sporty. Mix sharp observations with playful humor — think quick-witted sports commentator.",
  savage:
    "Go full savage roast mode. Be brutally funny, pull no punches. Demolish egos with creative insults while keeping it hilarious. Think comedy roast meets sports commentary.",
  "savage!!":
    "You are ABSOLUTELY UNHINGED. You are a drunk ESPN commentator possessed by a comedy writer narrating a padel match. Maximum creative aggression, career-ending burns, NO FILTER whatsoever. This is the PREMIUM experience. Go so far over the top that it loops back around to genius. Every sentence should be quotable. Make people spit out their drink reading this.",
};

interface RoastRequest {
  players: string[];
  scores: number[][];
  tone: string;
  trigger: string;
  tags?: string[];
  history_context?: string;
  tier: "free" | "pro_premium" | "pro_eco";
  locale?: string;
  game_type?: string;
  custom_context?: string;
}

function buildSystemPrompt(tone: string, locale: string): string {
  const languageName = LOCALE_MAP[locale] || LOCALE_MAP["en"];
  const toneInstruction =
    TONE_INSTRUCTIONS[tone] || TONE_INSTRUCTIONS["balanced"];

  return `You are PadelRoast — the world's most hilarious padel banter engine. Your job is to generate creative, shareable, and entertaining post-match roasts for padel players.

TONE DIRECTIVE:
${toneInstruction}

LANGUAGE DIRECTIVE:
Respond entirely in ${languageName}. All headlines, body text, WhatsApp messages, recaps, and MVP lines must be in ${languageName}.

RULES:
- Every roast must reference actual match details (scores, player names, trigger).
- Player titles should be creative, funny, and match-relevant.
- WhatsApp messages must include emojis and be ready to copy-paste.
- The match recap should read like a dramatic sports broadcast.
- The MVP line should be a comedic highlight of one standout moment or player.
- The rematch hype should taunt and provoke a rematch.
- You MUST return valid JSON matching the exact schema requested.
- Do NOT wrap the JSON in markdown code blocks.`;
}

function buildUserPrompt(req: RoastRequest): string {
  const [p1, p2, p3, p4] = req.players;
  const scoresFormatted = req.scores
    .map((s, i) => `Set ${i + 1}: ${s[0]}-${s[1]}`)
    .join(", ");
  const tagsLine =
    req.tags && req.tags.length > 0
      ? `Flavor tags: ${req.tags.join(", ")}`
      : "";
  const gameTypeLine = req.game_type
    ? `Game type: ${req.game_type}`
    : "";

  const customContextLine = req.custom_context
    ? `CUSTOM MATCH CONTEXT (integrate these specific details/funny moments into the roasts):\n"${req.custom_context}"`
    : "";

  const historyLine = req.history_context
    ? `PREVIOUS ENCOUNTERS & ROAST HISTORY (reference this history/rivalry to make it feel like a continuous saga. Avoid repeating these exact angles/jokes, but reference past results/roasts where appropriate):\n${req.history_context}`
    : "";

  return `Generate a padel match roast for this match. You must return EXACTLY 3 alternative roast cards in the "roast_cards" array, each presenting a different funny angle or comedic perspective of the match so the user can swipe between them.

Team A: ${p1} + ${p2}
Team B: ${p3} + ${p4}
Scores: ${scoresFormatted}
Detected trigger: ${req.trigger}
${tagsLine}
${gameTypeLine}
${customContextLine}
${historyLine}

Return ONLY a JSON object with this exact structure:
{
  "narrative_trigger_detected": "string — one of: Choke, Upset, Dominant, Revenge, Baseline, Bagel, Blowout, Comeback, Close",
  "player_titles_assigned": {
    "player_1": "string (custom funny title for ${p1})",
    "player_2": "string (custom funny title for ${p2})",
    "player_3": "string (custom funny title for ${p3})",
    "player_4": "string (custom funny title for ${p4})"
  },
  "roast_cards": [
    // Array must contain EXACTLY 3 objects with this structure, each with different content:
    {
      "headline": "string (catchy sporty headline)",
      "body_text": "string (detailed roast paragraph)",
      "pre_filled_whatsapp": "string (shareable WhatsApp message with emojis)",
      "match_recap": "string (3-sentence dramatic sports recap)",
      "mvp_line": "string (comedic MVP highlight)",
      "rematch_hype": "string (rematch call-out / taunt)"
    }
  ],
  "match_recap": "string (overall match recap)",
  "mvp_line": "string (overall MVP line)",
  "rematch_hype": "string (overall rematch hype)"
}`;

}

Deno.serve(async (req: Request) => {
  // Handle CORS preflight
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: CORS_HEADERS });
  }

  // Only allow POST
  if (req.method !== "POST") {
    return new Response(
      JSON.stringify({ error: "Method not allowed" }),
      {
        status: 405,
        headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
      },
    );
  }

  // Check for OpenAI API key
  const openaiKey = Deno.env.get("OPENAI_API_KEY");
  if (!openaiKey) {
    return new Response(
      JSON.stringify({ error: "OpenAI API key not configured" }),
      {
        status: 500,
        headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
      },
    );
  }

  let body: RoastRequest;
  try {
    body = await req.json();
  } catch {
    return new Response(
      JSON.stringify({ error: "Invalid JSON in request body" }),
      {
        status: 400,
        headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
      },
    );
  }

  // Validate required fields
  if (
    !body.players ||
    !Array.isArray(body.players) ||
    body.players.length < 4
  ) {
    return new Response(
      JSON.stringify({
        error: "Invalid request: 'players' must be an array of at least 4 names",
      }),
      {
        status: 400,
        headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
      },
    );
  }

  if (!body.scores || !Array.isArray(body.scores) || body.scores.length === 0) {
    return new Response(
      JSON.stringify({
        error: "Invalid request: 'scores' must be a non-empty array of score pairs",
      }),
      {
        status: 400,
        headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
      },
    );
  }

  // Determine model based on tier (free & pro_premium get gpt-5.4-mini, pro_eco gets throttled to gpt-4o-mini)
  const model = (body.tier === "pro_premium" || body.tier === "free") ? "gpt-5.4-mini" : "gpt-4o-mini";

  // Determine temperature based on tone
  const temperature = body.tone === "savage!!" ? 1.2 : 1.0;

  const locale = body.locale || "en";
  const tone = body.tone || "balanced";

  const systemPrompt = buildSystemPrompt(tone, locale);
  const userPrompt = buildUserPrompt(body);

  try {
    const openaiResponse = await fetch(
      "https://api.openai.com/v1/chat/completions",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${openaiKey}`,
        },
        body: JSON.stringify({
          model,
          messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userPrompt },
          ],
          response_format: { type: "json_object" },
          temperature,
        }),
      },
    );

    if (!openaiResponse.ok) {
      const errorBody = await openaiResponse.text();
      console.error("OpenAI API error:", openaiResponse.status, errorBody);
      return new Response(
        JSON.stringify({
          error: "OpenAI API error",
          status: openaiResponse.status,
          details: errorBody,
        }),
        {
          status: 502,
          headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
        },
      );
    }

    const openaiData = await openaiResponse.json();
    const content = openaiData.choices?.[0]?.message?.content;

    if (!content) {
      return new Response(
        JSON.stringify({ error: "No content in OpenAI response" }),
        {
          status: 502,
          headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
        },
      );
    }

    // Parse the JSON content from OpenAI
    let roastData;
    try {
      roastData = JSON.parse(content);
    } catch {
      console.error("Failed to parse OpenAI JSON response:", content);
      return new Response(
        JSON.stringify({
          error: "Failed to parse roast response",
          raw_content: content,
        }),
        {
          status: 502,
          headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
        },
      );
    }

    return new Response(JSON.stringify(roastData), {
      status: 200,
      headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
    });
  } catch (err) {
    console.error("Unexpected error:", err);
    return new Response(
      JSON.stringify({
        error: "Internal server error",
        message: err instanceof Error ? err.message : "Unknown error",
      }),
      {
        status: 500,
        headers: { ...CORS_HEADERS, "Content-Type": "application/json" },
      },
    );
  }
});
