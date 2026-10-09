import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
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
  ],
  authors: [{ name: "PadelRoast Team" }],
  openGraph: {
    title: "PadelRoast — Turn Every Padel Match Into Content Worth Sharing",
    description:
      "Brutal AI commentary, Elo adjustments, and hilarious roast cards for amateur padel matches.",
    url: "https://padelroast.com",
    siteName: "PadelRoast",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    title: "PadelRoast 🔥 Turn Matches into Comedy",
    description:
      "Instant AI roast cards and WhatsApp banter for amateur padel players.",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark scroll-smooth">
      <body className="antialiased selection:bg-[#CCFF00] selection:text-black min-h-screen flex flex-col bg-[#0B0F19]">
        {children}
      </body>
    </html>
  );
}
