"use client";

import React, { useState } from "react";
import { activeSport } from "@/config/sportConfig";
import { Flame, Sparkles, MessageCircle, AlertCircle } from "lucide-react";

export default function ToneShowcase() {
  const [selectedToneId, setSelectedToneId] = useState("savage");

  const currentTone =
    activeSport.humorTones.find((t) => t.id === selectedToneId) ||
    activeSport.humorTones[2];

  return (
    <section id="tones" className="py-20 bg-slate-950 border-y border-slate-800/80 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-14">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-500/10 border border-rose-500/30 text-rose-400 text-xs font-bold uppercase tracking-wider mb-3">
            <Flame className="w-3.5 h-3.5" />
            <span>Adjustable Banter Engine</span>
          </div>
          <h2 className="text-3xl sm:text-4xl font-black text-white tracking-tight">
            From Friendly Beers to{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-rose-500 via-amber-400 to-[#CCFF00]">
              Unhinged Destruction.
            </span>
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300">
            Playing with your boss? Dial it back to Casual. Playing against your childhood best friends who talked trash all week? Unleash Savage!!.
          </p>
        </div>

        {/* Tone Selector Tabs */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3 max-w-4xl mx-auto mb-10">
          {activeSport.humorTones.map((tone) => {
            const isSelected = selectedToneId === tone.id;
            return (
              <button
                key={tone.id}
                onClick={() => setSelectedToneId(tone.id)}
                className={`p-4 rounded-2xl border text-left transition-all duration-200 cursor-pointer ${
                  isSelected
                    ? "bg-slate-900 border-[#CCFF00] shadow-[0_0_20px_rgba(204,255,0,0.15)] -translate-y-1"
                    : "bg-slate-900/40 border-slate-800/80 hover:bg-slate-900/80 hover:border-slate-700"
                }`}
              >
                <div className="text-lg mb-1.5">{tone.badge.split(" ")[0]}</div>
                <div className="font-extrabold text-sm text-white">{tone.name}</div>
                <div className="text-xs text-slate-400 mt-1 line-clamp-1">{tone.description}</div>
              </button>
            );
          })}
        </div>

        {/* Interactive Tone Preview Card */}
        <div className="max-w-3xl mx-auto bg-gradient-to-b from-slate-900 to-[#131B2E] border-2 border-slate-800 rounded-3xl p-6 sm:p-8 shadow-2xl relative overflow-hidden">
          
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
            <div>
              <span className="text-xs font-bold text-slate-400 uppercase tracking-widest block mb-1">
                Active Tone Mode
              </span>
              <div className="flex items-center gap-2">
                <h3 className="text-2xl font-black text-white">{currentTone.name}</h3>
                <span className="text-xs font-bold px-2.5 py-1 rounded-full bg-slate-800 border border-slate-700 text-[#00E5FF]">
                  {currentTone.badge}
                </span>
              </div>
            </div>

            <span className="text-xs font-medium text-slate-400 max-w-xs sm:text-right">
              {currentTone.description}
            </span>
          </div>

          {/* Sample Quote Box */}
          <div className="mt-6 bg-[#0B0F19] rounded-2xl p-6 border border-slate-800 relative">
            <div className="text-xs font-bold uppercase text-[#CCFF00] tracking-wider mb-2 flex items-center gap-1.5">
              <Sparkles className="w-3.5 h-3.5" />
              Generated Match Roast Sample
            </div>
            <p className="text-lg sm:text-xl font-semibold text-slate-100 italic leading-relaxed">
              {currentTone.sampleQuote}
            </p>
          </div>

          {/* WhatsApp share snippet preview */}
          <div className="mt-4 flex items-center justify-between text-xs text-slate-400">
            <span className="flex items-center gap-1.5">
              <MessageCircle className="w-4 h-4 text-emerald-400" />
              Includes copy-ready WhatsApp formatting with match stats
            </span>
            <span className="text-[#CCFF00] font-bold">100% AI Custom Generated</span>
          </div>
        </div>

      </div>
    </section>
  );
}
