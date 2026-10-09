"use client";

import React from "react";
import { Users, XCircle, CheckCircle2 } from "lucide-react";
import { useLanguage } from "@/context/LanguageContext";

export default function GhostProfileFeature() {
  const { t } = useLanguage();

  return (
    <section id="ghost-profiles" className="py-24 bg-[#0B0F19] relative overflow-hidden">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-[#00E5FF]/15 border border-[#00E5FF]/40 text-[#00E5FF] text-xs font-black uppercase tracking-wider mb-3">
            <Users className="w-3.5 h-3.5" />
            <span>{t.ghostSection.badge}</span>
          </div>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight uppercase">
            {t.ghostSection.titleMain}{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#00E5FF] to-[#CCFF00]">
              {t.ghostSection.titleHighlight}
            </span>
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300 font-medium">
            {t.ghostSection.subtitle}
          </p>
        </div>

        {/* Side-by-side Comparison Grid: Brutally honest */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-5xl mx-auto">
          
          {/* Other Apps: Frustrating */}
          <div className="bg-rose-950/20 border-2 border-rose-900/60 rounded-3xl p-8 shadow-xl flex flex-col justify-between">
            <div>
              <div className="flex items-center justify-between mb-6">
                <span className="text-xs font-black uppercase tracking-wider px-3 py-1 rounded-full bg-rose-500/20 text-rose-400 border border-rose-500/30">
                  {t.ghostSection.otherAppsBadge}
                </span>
                <XCircle className="w-6 h-6 text-rose-500" />
              </div>

              <h3 className="text-xl font-black text-white mb-4">
                {t.ghostSection.otherAppsTitle}
              </h3>

              <div className="space-y-3.5 text-xs sm:text-sm text-slate-300">
                {t.ghostSection.otherAppsBullets.map((bullet, idx) => (
                  <div key={idx} className="flex items-start gap-2.5">
                    <span className="text-rose-400 font-bold shrink-0">✕</span>
                    <span>{bullet}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="mt-8 pt-4 border-t border-rose-900/40 text-xs text-rose-300/80 italic font-medium">
              {t.ghostSection.otherAppsResult}
            </div>
          </div>

          {/* PadelRoast: Frictionless & Fun */}
          <div className="bg-gradient-to-b from-slate-900 to-[#0F172A] border-2 border-[#CCFF00] rounded-3xl p-8 shadow-[0_0_35px_rgba(204,255,0,0.15)] flex flex-col justify-between relative">
            <div className="absolute -top-3.5 -right-3.5 bg-[#CCFF00] text-black font-black text-xs uppercase px-3 py-1 rounded-full shadow-md border-2 border-black rotate-3">
              {t.ghostSection.padelRoastBadge}
            </div>

            <div>
              <div className="flex items-center justify-between mb-6">
                <span className="text-xs font-black uppercase tracking-wider px-3 py-1 rounded-full bg-[#CCFF00]/20 text-[#CCFF00] border border-[#CCFF00]/40">
                  {t.ghostSection.padelRoastTag}
                </span>
                <CheckCircle2 className="w-6 h-6 text-[#CCFF00]" />
              </div>

              <h3 className="text-xl font-black text-white mb-4">
                {t.ghostSection.padelRoastTitle}
              </h3>

              <div className="space-y-3.5 text-xs sm:text-sm text-slate-200">
                {t.ghostSection.padelRoastBullets.map((bullet, idx) => (
                  <div key={idx} className="flex items-start gap-2.5">
                    <span className="text-[#CCFF00] font-black shrink-0">✓</span>
                    <span><strong>{bullet.strong}</strong> {bullet.text}</span>
                  </div>
                ))}
              </div>
            </div>

            <div className="mt-8 pt-4 border-t border-slate-800 text-xs text-[#CCFF00] font-black uppercase tracking-wider">
              {t.ghostSection.padelRoastResult}
            </div>
          </div>

        </div>

      </div>
    </section>
  );
}
