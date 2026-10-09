"use client";

import React, { useState } from "react";
import Image from "next/image";
import { activeSport } from "@/config/sportConfig";
import { Flame, Sparkles, MessageCircle, Volume2, Skull, Beer, Trophy, AlertOctagon } from "lucide-react";

export default function ToneShowcase() {
  const [selectedToneIndex, setSelectedToneIndex] = useState(2); // default Savage

  const tones = [
    {
      id: "casual",
      name: "Casual Banter",
      level: "Level 1",
      badge: "🍻 Beer & Tapas",
      icon: <Beer className="w-5 h-5 text-amber-400" />,
      color: "border-amber-400/40 text-amber-400",
      vibe: "For games with your boss or in-laws. Light teasing, zero ruined relationships.",
      quote: "“Solid game out there boys. Carlos and Marcos took the win 6-3, but David and Alex definitely won the award for most color-coordinated outfits.”",
      whatsapp: "Good game today! Carlos & Marcos take it 6-3. Beers are ready at the clubhouse 🍻🎾",
    },
    {
      id: "balanced",
      name: "Sports Desk",
      level: "Level 2",
      badge: "🎙️ ESPN Breakdown",
      icon: <Trophy className="w-5 h-5 text-[#00E5FF]" />,
      color: "border-[#00E5FF]/40 text-[#00E5FF]",
      vibe: "Analytical sports humor. Points out high unforced errors, tragic shot selection, and Sunday hobbyist energy.",
      quote: "“A tactical disasterclass on court 3. High unforced error count, questionable lob angles, and peak amateur decision-making under pressure.”",
      whatsapp: "Match analysis in: Carlos & Marcos dominate court 3. David's unforced error percentage currently under review by stats team 📊",
    },
    {
      id: "savage",
      name: "Savage",
      level: "Level 3",
      badge: "🔥 Maximum Burn",
      icon: <Flame className="w-5 h-5 text-[#FF3366] fill-[#FF3366]" />,
      color: "border-[#FF3366] text-[#FF3366]",
      vibe: "Hits right in the ego. Demolishes expensive equipment, fake calf cramps, and delusions of turning pro.",
      quote: "“David spent €380 on a carbon fiber racket just to hit the side fence 16 times. Alex was spotted googling tennis lessons in the parking lot before the handshake.”",
      whatsapp: "Rest in peace boys. Carlos & Marcos 6-1 6-0. David, does your racket come with a return policy? 😂💀",
    },
    {
      id: "unhinged",
      name: "Savage!!",
      level: "Level 4",
      badge: "💀 Unhinged Meltdown",
      icon: <Skull className="w-5 h-5 text-rose-500 fill-rose-500" />,
      color: "border-rose-500 text-rose-500",
      vibe: "Full theatrical meltdown. Hyperbolic, aggressive, completely hilarious. For your closest friends only.",
      quote: "“CALL THE FEDERATION. We witnessed an absolute tragedy. Delete Playtomic, burn your shoes, and take up competitive birdwatching immediately.”",
      whatsapp: "EMERGENCY POLICE REPORT FROM COURT 3: 6-0 6-0. Sell your gear on Wallapop right now, you are banned from the club 💀😭",
    },
  ];

  const current = tones[selectedToneIndex];

  return (
    <section id="tones" className="py-24 bg-slate-950 border-y border-slate-800/80 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-500/10 border border-rose-500/30 text-rose-400 text-xs font-black uppercase tracking-wider mb-3">
            <Flame className="w-3.5 h-3.5 fill-rose-500" />
            <span>The Banter Volume Knob</span>
          </div>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight uppercase">
            From Friendly Beers to{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-rose-500 via-amber-400 to-[#CCFF00]">
              Unhinged Destruction.
            </span>
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300 font-medium">
            Slide the dial to control the brutality level. Choose who gets roasted and how hard.
          </p>
        </div>

        {/* The Physical DJ Mixer / Slider Bar */}
        <div className="max-w-3xl mx-auto mb-10 bg-slate-900/90 border-2 border-slate-800 p-2 rounded-2xl flex items-center justify-between gap-2 shadow-xl">
          {tones.map((t, idx) => (
            <button
              key={t.id}
              onClick={() => setSelectedToneIndex(idx)}
              className={`flex-1 py-3 px-2 rounded-xl text-center transition-all cursor-pointer ${
                selectedToneIndex === idx
                  ? "bg-[#CCFF00] text-black font-black shadow-lg scale-[1.03]"
                  : "text-slate-400 hover:text-white hover:bg-slate-800/60 font-bold"
              }`}
            >
              <div className="text-xs uppercase tracking-wider mb-0.5">{t.level}</div>
              <div className="text-xs sm:text-sm font-black truncate">{t.name}</div>
            </button>
          ))}
        </div>

        {/* Interactive Tone Preview Board */}
        <div className="max-w-4xl mx-auto bg-gradient-to-b from-slate-900 to-[#0F172A] border-2 border-slate-700/80 rounded-3xl p-6 sm:p-10 shadow-2xl relative overflow-hidden">
          
          {/* Top Info Strip */}
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
            <div className="flex items-center gap-3">
              <div className="w-12 h-12 rounded-2xl bg-black border border-slate-700 flex items-center justify-center shrink-0 shadow-md">
                {current.icon}
              </div>
              <div>
                <div className="flex items-center gap-2">
                  <h3 className="text-2xl font-black text-white">{current.name}</h3>
                  <span className="text-xs font-black uppercase px-2.5 py-0.5 rounded-full bg-black/60 border border-slate-700 text-[#CCFF00]">
                    {current.badge}
                  </span>
                </div>
                <p className="text-xs text-slate-400 mt-1 font-medium">{current.vibe}</p>
              </div>
            </div>

            <div className="text-left sm:text-right">
              <span className="text-[10px] font-black uppercase tracking-wider text-slate-500 block">
                Intensity Level
              </span>
              <span className="text-sm font-black text-[#00E5FF] font-mono">
                {selectedToneIndex + 1} / 4
              </span>
            </div>
          </div>

          {/* Generated Quote Box with Streetwear Label */}
          <div className="mt-8 bg-black/80 rounded-2xl p-6 sm:p-8 border border-slate-800 relative shadow-inner">
            <div className="flex items-center justify-between mb-4">
              <span className="text-[10px] font-black uppercase tracking-widest text-[#CCFF00] flex items-center gap-1.5">
                <Sparkles className="w-3.5 h-3.5" />
                Actual AI Generated Output
              </span>
              <span className="text-[10px] font-mono text-slate-400">
                PadelRoast LLM Engine
              </span>
            </div>

            <p className="text-lg sm:text-2xl font-bold text-white leading-relaxed italic">
              {current.quote}
            </p>
          </div>

          {/* WhatsApp formatted output */}
          <div className="mt-6 flex flex-col sm:flex-row sm:items-center justify-between gap-4 p-4 rounded-xl bg-slate-950/70 border border-slate-800 text-xs">
            <div className="flex items-center gap-2 text-slate-300 font-medium">
              <MessageCircle className="w-4 h-4 text-emerald-400 shrink-0" />
              <span><strong>Pre-filled WhatsApp message:</strong> &ldquo;{current.whatsapp}&rdquo;</span>
            </div>
            <span className="text-[#CCFF00] font-black uppercase text-[10px] shrink-0">
              1-Tap Ready
            </span>
          </div>

        </div>

      </div>
    </section>
  );
}
