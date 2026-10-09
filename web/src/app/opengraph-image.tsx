import { ImageResponse } from "next/og";

export const runtime = "edge";
export const alt = "PadelRoast — Turn Every Padel Match Into Content Worth Sharing";
export const size = {
  width: 1200,
  height: 630,
};
export const contentType = "image/png";

export default async function Image() {
  return new ImageResponse(
    (
      <div
        style={{
          height: "100%",
          width: "100%",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          backgroundColor: "#0B0F19",
          backgroundImage:
            "radial-gradient(circle at 50% 40%, rgba(204,255,0,0.18) 0%, rgba(0,229,255,0.1) 45%, #0B0F19 100%)",
          padding: "40px",
        }}
      >
        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: "16px",
            marginBottom: "20px",
          }}
        >
          <div style={{ fontSize: "52px" }}>🔥</div>
          <div
            style={{
              fontSize: "64px",
              fontWeight: 900,
              color: "#FFFFFF",
              letterSpacing: "-0.03em",
            }}
          >
            PADEL<span style={{ color: "#CCFF00" }}>ROAST</span>
          </div>
        </div>

        <div
          style={{
            fontSize: "44px",
            fontWeight: 800,
            color: "#FFFFFF",
            textAlign: "center",
            maxWidth: "960px",
            lineHeight: 1.2,
            marginBottom: "16px",
          }}
        >
          Turn every match into{" "}
          <span style={{ color: "#CCFF00" }}>content worth sharing.</span>
        </div>

        <div
          style={{
            fontSize: "24px",
            color: "#94A3B8",
            textAlign: "center",
            maxWidth: "800px",
            marginBottom: "36px",
          }}
        >
          Instant AI roast cards and WhatsApp banter for amateur padel players.
        </div>

        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: "18px",
          }}
        >
          <div
            style={{
              backgroundColor: "#CCFF00",
              color: "#000000",
              fontSize: "20px",
              fontWeight: 900,
              padding: "12px 28px",
              borderRadius: "14px",
            }}
          >
            Download Android Beta
          </div>
          <div
            style={{
              backgroundColor: "#131B2E",
              color: "#00E5FF",
              fontSize: "20px",
              fontWeight: 800,
              padding: "12px 24px",
              borderRadius: "14px",
              border: "1px solid #1E293B",
            }}
          >
            padelroast.com
          </div>
        </div>
      </div>
    ),
    {
      ...size,
    }
  );
}
