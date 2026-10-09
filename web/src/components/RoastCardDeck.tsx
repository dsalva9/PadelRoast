"use client";

import React, { useState } from "react";
import Image from "next/image";
import { activeSport } from "@/config/sportConfig";
import { Flame, Copy, Check, Sparkles, Skull, Trophy, AlertTriangle } from "lucide-react";

export default function RoastCardDeck() {
  const [copiedIndex, setCopiedIndex] = useState<number | null>(null);

  const handleCopy = (text: string, index: number) => {
    navigator.clipboard.writeText(text);
    setCopiedIndex(index);
    setTimeout(() => setCopiedIndex(null), 2500);
  };

  const visualCards = [
    {
      title: "The All-Kit No-Hit Phenomenon",
      subtitle: "Player of the Match (Victim Edition)",
      badge: "BAGEL VICTIM 🥯",
      badgeColor: "bg-[#FF3366] text-white",
      image: "/images/padel-player-shock.jpg",
      score: "6-0, 6-1",
      quote: "Alex arrived with pro shoes, matching wristbands, and a €380 carbon racket, only to produce 38 unforced errors and 0 winners.",
      whatsapp: "🚨 Court 3 Tragedy: Alex spent €380 on Bullpadel gear just to donate 38 balls to the side fence. Rest in peace brother 😂💀",
      awardLabel: "Special Citation",
      awardText: "Most Expensive Donation of Padel Balls",
    },
    {
      title: "The Fried Egg Trophy",
      subtitle: "Official Roast Deck MVP",
      badge: "GOLDEN BAGEL 🏆",
      badgeColor: "bg-[#CCFF00] text-black",
      image: "/images/bagel-trophy.jpg",
      score: "6-1, 6-0",
      quote: "Awarded to David & Marcos for scoring exactly zero points on return and spending the third set arguing whether the glass was dirty.",
      whatsapp: "🏆 Award Ceremony: David & Marcos take home the Golden Bagel! Beers on you guys tonight 🍻🍳",
      awardLabel: "Official Award",
      awardText: "The Lifetime Achievement in Net Shanking",
    },
    {
      title: "Court 3 Crime Scene Report",
      subtitle: "Confiscated Equipment",
      badge: "FENCE SMASHER 🔥",
      badgeColor: "bg-[#00E5FF] text-black",
      image: "/images/padel-racket-fire.jpg",
      score: "7-5, 6-1",
      quote: "Police called to court 3 after Carlos hit the glass so violently the neighboring tennis club filed an earthquake tremor report.",
      whatsapp: "Carlos's smash just passed low earth orbit 🚀 Neighbors thought it was thunder. Good win boys! 🎾⚡",
      awardLabel: "Ballistic Analysis",
      awardText: "14 Direct Hits on the Metal Enclosure",
    },
  ];

  return (
    <section id="cards" className="py-24 bg-slate-950 border-t border-slate-800/80 relative overflow-hidden">
      {/* Background Graphic Accents */}
      <div className="absolute top-1/3 left-0 w-80 h-80 bg-[#CCFF00]/5 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-10 right-0 w-80 h-80 bg-[#00E5FF]/5 rounded-full blur-3xl pointer-events-none" />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        
        {/* Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-[#CCFF00]/15 border border-[#CCFF00]/40 text-[#CCFF00] text-xs font-black uppercase tracking-wider mb-3">
            <Sparkles className="w-3.5 h-3.5" />
            <span>The Hero Output</span>
          </div>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight uppercase">
            Cards worth bragging{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#CCFF00] via-amber-300 to-[#FF3366]">
              (or crying) about.
            </span>
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300 font-medium">
            Every match generates high-resolution collectible cards customized with your friends&apos; names and actual mistakes.
          </p>
        </div>

        {/* 3 Rich Visual Graphic Cards Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {visualCards.map((card, idx) => (
            <div
              key={idx}
              className="bg-slate-900 border-2 border-slate-800 hover:border-[#CCFF00]/80 rounded-[2rem] overflow-hidden flex flex-col justify-between shadow-2xl transition-all duration-300 hover:-translate-y-2 hover:shadow-[0_15px_40px_rgba(204,255,0,0.15)] group relative"
            >
              <div>
                {/* Visual Image Header */}
                <div className="relative h-60 w-full overflow-hidden bg-slate-950">
                  <Image
                    src={card.image}
                    alt={card.title}
                    fill
                    className="object-cover group-hover:scale-105 transition-transform duration-500"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-slate-900 via-transparent to-black/40" />

                  {/* Top Badge Stamp */}
                  <div className="absolute top-3 left-3">
                    <span className={`text-[10px] font-black uppercase tracking-wider px-2.5 py-1 rounded-md shadow-md border border-black/30 ${card.badgeColor}`}>
                      {card.badge}
                    </span>
                  </div>

                  {/* Score Pill */}
                  <div className="absolute top-3 right-3 bg-black/80 backdrop-blur-md px-2.5 py-1 rounded-md border border-white/20 font-mono text-xs font-black text-[#00E5FF]">
                    {card.score}
                  </div>

                  {/* Subtitle tag overlay */}
                  <div className="absolute bottom-2 left-3 right-3 flex items-center justify-between text-[11px] font-bold text-slate-300">
                    <span>{card.subtitle}</span>
                  </div>
                </div>

                {/* Card Content Body */}
                <div className="p-6">
                  <h3 className="text-xl font-black text-white group-hover:text-[#CCFF00] transition-colors leading-tight mb-3">
                    {card.title}
                  </h3>

                  <p className="text-xs sm:text-sm text-slate-300 leading-relaxed font-normal mb-5">
                    &ldquo;{card.quote}&rdquo;
                  </p>

                  {/* Award Box */}
                  <div className="bg-slate-950 p-3 rounded-xl border border-slate-800 mb-2">
                    <div className="text-[9px] uppercase font-black text-slate-400">
                      {card.awardLabel}
                    </div>
                    <div className="text-xs font-bold text-[#00E5FF] mt-0.5">
                      {card.awardText}
                    </div>
                  </div>
                </div>
              </div>

              {/* Bottom Action */}
              <div className="p-6 pt-0">
                <button
                  onClick={() => handleCopy(card.whatsapp, idx)}
                  className="w-full py-3 px-4 rounded-xl bg-slate-800 hover:bg-[#CCFF00] hover:text-black text-white font-black text-xs flex items-center justify-center gap-2 transition-all cursor-pointer shadow-sm active:scale-95"
                >
                  {copiedIndex === idx ? (
                    <>
                      <Check className="w-4 h-4 text-black" />
                      <span className="text-black font-extrabold">Copied to Clipboard!</span>
                    </>
                  ) : (
                    <>
                      <Copy className="w-4 h-4" />
                      <span>Copy WhatsApp Banter</span>
                    </>
                  )}
                </button>
              </div>

            </div>
          ))}
        </div>

      </div>
    </section>
  );
}
