import Navbar from "@/components/Navbar";
import Hero from "@/components/Hero";
import ToneShowcase from "@/components/ToneShowcase";
import GhostProfileFeature from "@/components/GhostProfileFeature";
import RoastCardDeck from "@/components/RoastCardDeck";
import WhatsAppPreview from "@/components/WhatsAppPreview";
import DownloadCTA from "@/components/DownloadCTA";
import FAQ from "@/components/FAQ";
import Footer from "@/components/Footer";

export default function Home() {
  return (
    <main className="min-h-screen bg-[#0B0F19] text-white flex flex-col">
      <Navbar />
      <Hero />
      <ToneShowcase />
      <GhostProfileFeature />
      <RoastCardDeck />
      <WhatsAppPreview />
      <DownloadCTA />
      <FAQ />
      <Footer />
    </main>
  );
}
