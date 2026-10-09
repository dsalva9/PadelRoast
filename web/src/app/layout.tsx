import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  metadataBase: new URL("https://padelroast.com"),
  title: "PadelRoast — Turn Every Padel Match Into Content Worth Sharing",
  description:
    "Log your amateur padel match in 10 seconds. Generate hilarious, shareable AI roast cards and drop them straight into your WhatsApp group chat.",
  keywords: [
    "padel",
    "padel roast",
    "padel banter",
    "roast card",
    "padel match generator",
    "padel whatsapp",
    "amateur padel",
    "padel club",
    "padel meme",
  ],
  authors: [{ name: "PadelRoast Team" }],
  alternates: {
    canonical: "/",
  },
  openGraph: {
    title: "PadelRoast — Turn Every Padel Match Into Content Worth Sharing",
    description:
      "Brutal AI commentary, Elo adjustments, and hilarious roast cards for amateur padel matches.",
    url: "https://padelroast.com",
    siteName: "PadelRoast",
    type: "website",
    locale: "en_US",
  },
  twitter: {
    card: "summary_large_image",
    title: "PadelRoast 🔥 Turn Matches into Comedy",
    description:
      "Instant AI roast cards and WhatsApp banter for amateur padel players.",
  },
  robots: {
    index: true,
    follow: true,
    googleBot: {
      index: true,
      follow: true,
      "max-image-preview": "large",
      "max-video-preview": -1,
      "max-snippet": -1,
    },
  },
};

const jsonLd = {
  "@context": "https://schema.org",
  "@type": "SoftwareApplication",
  name: "PadelRoast",
  operatingSystem: "Android",
  applicationCategory: "SportsApplication",
  offers: {
    "@type": "Offer",
    price: "0",
    priceCurrency: "EUR",
  },
  description:
    "Amateur padel match logging and comedic AI roast card generator for WhatsApp groups.",
  url: "https://padelroast.com",
  featureList: [
    "10-second match score input",
    "4 comedic banter intensity levels (Casual to Savage!!)",
    "Ghost profiles for zero-friction player setup",
    "1-tap WhatsApp card sharing",
    "Elo tracking and match history",
  ],
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark scroll-smooth">
      <head>
        <script
          type="application/ld+json"
          dangerouslySetInnerHTML={{ __html: JSON.stringify(jsonLd) }}
        />
      </head>
      <body className="antialiased selection:bg-[#CCFF00] selection:text-black min-h-screen flex flex-col bg-[#0B0F19]">
        {children}
      </body>
    </html>
  );
}
