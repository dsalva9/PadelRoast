"use client";

import React, { useState } from "react";
import { Flame, Sparkles, Skull, Beer, Trophy, Copy, Check } from "lucide-react";
import { useLanguage } from "@/context/LanguageContext";

export default function ToneShowcase() {
  const { t } = useLanguage();
  const [selectedToneIndex, setSelectedToneIndex] = useState(2); // default Savage
  const [copied, setCopied] = useState(false);

  const toneIcons = [
    <Beer key="beer" className="w-5 h-5 text-amber-400" />,
    <Trophy key="trophy" className="w-5 h-5 text-[#00E5FF]" />,
    <Flame key="flame" className="w-5 h-5 text-[#FF3366] fill-[#FF3366]" />,
    <Skull key="skull" className="w-5 h-5 text-rose-500 fill-rose-500" />,
  ];

  const tones = t.tonesSection.tones;
  const currentToneIndex = selectedToneIndex % tones.length;
  const current = tones[currentToneIndex] || tones[0];

  const handleCopy = (text: string) => {
    navigator.clipboard.writeText(text);
    setCopied(true);
    setTimeout(() => setCopied(false), 2200);
  };

  return (
    <section id="tones" className="py-24 bg-slate-950 border-y border-slate-800/80 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-500/10 border border-rose-500/30 text-rose-400 text-xs font-black uppercase tracking-wider mb-3">
            <Flame className="w-3.5 h-3.5 fill-rose-500" />
            <span>{t.tonesSection.badge}</span>
          </div>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight uppercase">
            {t.tonesSection.titleMain}{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-rose-500 via-amber-400 to-[#CCFF00]">
              {t.tonesSection.titleHighlight}
            </span>
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300 font-medium">
            {t.tonesSection.subtitle}
          </p>
        </div>

        {/* The Physical DJ Mixer / Slider Bar */}
        <div className="max-w-3xl mx-auto mb-10 bg-slate-900/90 border-2 border-slate-800 p-2 rounded-2xl flex items-center justify-between gap-2 shadow-xl">
          {tones.map((tone, idx) => (
            <button
              key={tone.id}
              onClick={() => setSelectedToneIndex(idx)}
              className={`flex-1 py-3 px-2 rounded-xl text-center transition-all cursor-pointer ${
                currentToneIndex === idx
                  ? "bg-[#CCFF00] text-black font-black shadow-lg scale-[1.03]"
                  : "text-slate-400 hover:text-white hover:bg-slate-800/60 font-bold"
              }`}
            >
              <div className="text-xs uppercase tracking-wider mb-0.5">{tone.level}</div>
              <div className="text-xs sm:text-sm font-black truncate">{tone.name}</div>
            </button>
          ))}
        </div>

        {/* Interactive Tone Preview Board */}
        <div className="max-w-4xl mx-auto bg-gradient-to-b from-slate-900 to-[#0F172A] border-2 border-slate-700/80 rounded-3xl p-6 sm:p-10 shadow-2xl relative overflow-hidden">
          
          {/* Top Info Strip */}
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-6 border-b border-slate-800">
            <div className="flex items-center gap-3">
              <div className="w-12 h-12 rounded-2xl bg-black border border-slate-700 flex items-center justify-center shrink-0 shadow-md">
                {toneIcons[currentToneIndex]}
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
                {t.tonesSection.intensityLabel}
              </span>
              <span className="text-sm font-black text-[#00E5FF] font-mono">
                {currentToneIndex + 1} / 4
              </span>
            </div>
          </div>

          {/* Generated Quote Box with Streetwear Label */}
          <div className="mt-8 bg-black/80 rounded-2xl p-6 sm:p-8 border border-slate-800 relative shadow-inner">
            <div className="flex items-center justify-between mb-4">
              <span className="text-[10px] font-black uppercase tracking-widest text-[#CCFF00] flex items-center gap-1.5">
                <Sparkles className="w-3.5 h-3.5" />
                {t.tonesSection.aiOutputLabel}
              </span>
              <span className="text-[10px] font-mono text-slate-400">
                {t.tonesSection.engineLabel}
              </span>
            </div>

            <p className="text-lg sm:text-2xl font-black text-white leading-relaxed italic">
              {current.quote}
            </p>
          </div>

          {/* WhatsApp Sample Message Strip */}
          <div className="mt-6 p-4 rounded-2xl bg-[#0b141a] border border-emerald-900/60 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
            <div className="space-y-1">
              <div className="text-[10px] uppercase font-bold text-emerald-400">
                {t.tonesSection.prefilledLabel}
              </div>
              <div className="text-xs sm:text-sm text-slate-200">
                &ldquo;{current.whatsapp}&rdquo;
              </div>
            </div>

            <button
              onClick={() => handleCopy(current.whatsapp)}
              className="px-4 py-2 rounded-xl bg-slate-800 hover:bg-[#CCFF00] hover:text-black text-white font-black text-xs shrink-0 flex items-center gap-1.5 transition-colors cursor-pointer"
            >
              {copied ? (
                <>
                  <Check className="w-3.5 h-3.5" />
                  <span>{t.cardsSection.copiedBtn}</span>
                </>
              ) : (
                <>
                  <Copy className="w-3.5 h-3.5" />
                  <span>{t.tonesSection.readyLabel}</span>
                </>
              )}
            </button>
          </div>

        </div>

      </div>
    </section>
  );
}
