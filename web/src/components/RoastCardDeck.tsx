"use client";

import React, { useState } from "react";
import { activeSport } from "@/config/sportConfig";
import { Flame, Share2, Copy, Check, Sparkles } from "lucide-react";

export default function RoastCardDeck() {
  const [copiedIndex, setCopiedIndex] = useState<number | null>(null);

  const handleCopy = (text: string, index: number) => {
    navigator.clipboard.writeText(text);
    setCopiedIndex(index);
    setTimeout(() => setCopiedIndex(null), 2500);
  };

  return (
    <section id="cards" className="py-20 bg-slate-950 border-t border-slate-800/80 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-14">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-[#CCFF00]/10 border border-[#CCFF00]/30 text-[#CCFF00] text-xs font-bold uppercase tracking-wider mb-3">
            <Sparkles className="w-3.5 h-3.5" />
            <span>The Hero Output</span>
          </div>
          <h2 className="text-3xl sm:text-4xl font-black text-white tracking-tight">
            Roast Cards Worth Bragging (or Crying) About.
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300">
            Every match generates multiple alternative cards. Tap any card below to see the exact text that goes straight into your group chat.
          </p>
        </div>

        {/* 3 Sample Cards Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {activeSport.roastSamples.map((card, idx) => (
            <div
              key={idx}
              className="bg-gradient-to-b from-slate-900 to-[#111827] border-2 border-slate-800 hover:border-[#CCFF00]/60 rounded-3xl p-6 flex flex-col justify-between shadow-xl transition-all duration-300 hover:-translate-y-1 hover:shadow-[0_10px_30px_rgba(204,255,0,0.12)] group"
            >
              <div>
                {/* Header Tag & Score */}
                <div className="flex items-center justify-between mb-4">
                  <span className="text-[10px] font-black uppercase tracking-wider px-2.5 py-1 rounded-lg bg-[#CCFF00]/15 text-[#CCFF00] border border-[#CCFF00]/30">
                    {card.tag}
                  </span>
                  <span className="text-xs font-mono font-bold text-[#00E5FF]">
                    {card.score}
                  </span>
                </div>

                {/* Teams */}
                <div className="text-xs text-slate-400 font-semibold mb-2 flex items-center justify-between">
                  <span className="text-white font-bold">{card.teams.teamA}</span>
                  <span className="text-slate-600">vs</span>
                  <span className="text-slate-400">{card.teams.teamB}</span>
                </div>

                {/* Headline */}
                <h3 className="text-lg font-black text-white group-hover:text-[#CCFF00] transition-colors leading-tight mb-3">
                  &ldquo;{card.headline}&rdquo;
                </h3>

                {/* Body Text */}
                <p className="text-sm text-slate-300 leading-relaxed mb-4">
                  {card.body}
                </p>
              </div>

              <div>
                {/* MVP Line Box */}
                <div className="bg-slate-950 p-3 rounded-xl border border-slate-800/80 mb-4">
                  <span className="text-[9px] font-extrabold uppercase text-slate-400 block mb-0.5">
                    Match MVP Award
                  </span>
                  <span className="text-xs font-semibold text-[#00E5FF]">
                    {card.mvp}
                  </span>
                </div>

                {/* Copy Button */}
                <button
                  onClick={() => handleCopy(card.whatsappMessage, idx)}
                  className="w-full py-2.5 px-4 rounded-xl bg-slate-800 hover:bg-slate-700 text-white font-bold text-xs flex items-center justify-center gap-2 transition-colors cursor-pointer"
                >
                  {copiedIndex === idx ? (
                    <>
                      <Check className="w-3.5 h-3.5 text-[#CCFF00]" />
                      <span className="text-[#CCFF00]">Copied for WhatsApp!</span>
                    </>
                  ) : (
                    <>
                      <Copy className="w-3.5 h-3.5" />
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
