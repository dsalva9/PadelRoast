"use client";

import React, { useState } from "react";
import { Download, ShieldCheck, CheckCircle2, Flame, Smartphone } from "lucide-react";
import { useLanguage } from "@/context/LanguageContext";

export default function DownloadCTA() {
  const { t } = useLanguage();
  const [downloadStarted, setDownloadStarted] = useState(false);

  const handleApkDownload = () => {
    setDownloadStarted(true);
    const link = document.createElement("a");
    link.href = "/apk/padelroast-beta.apk";
    link.download = "padelroast-beta.apk";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <section id="download" className="py-24 relative bg-slate-950 border-t border-slate-800/80 overflow-hidden">
      
      {/* Ambient Glow */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-full max-w-5xl h-96 bg-gradient-to-r from-[#CCFF00]/15 via-rose-500/10 to-[#00E5FF]/15 blur-3xl pointer-events-none" />

      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        
        <div className="bg-gradient-to-b from-slate-900 via-slate-900 to-[#0B0F19] border-2 border-slate-700/80 rounded-3xl p-8 sm:p-14 text-center shadow-[0_0_60px_rgba(0,0,0,0.9)] relative overflow-hidden">
          
          <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-[#CCFF00]/15 border border-[#CCFF00]/40 text-[#CCFF00] text-xs font-black uppercase tracking-wider mb-6">
            <Flame className="w-4 h-4 fill-[#CCFF00]" />
            <span>{t.downloadSection.badge}</span>
          </div>

          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight max-w-2xl mx-auto uppercase leading-tight">
            {t.downloadSection.title}
          </h2>

          <p className="mt-4 text-base sm:text-lg text-slate-300 max-w-xl mx-auto font-medium">
            {t.downloadSection.subtitle}
          </p>

          {/* Download Buttons Group */}
          <div className="mt-8 flex flex-col sm:flex-row items-center justify-center gap-4 max-w-md mx-auto">
            
            {/* Direct APK Button */}
            <button
              onClick={handleApkDownload}
              className="w-full sm:w-auto flex-1 inline-flex items-center justify-center gap-3 bg-[#CCFF00] hover:bg-[#b8e600] text-black font-black text-base px-7 h-15 rounded-2xl shadow-[0_0_35px_rgba(204,255,0,0.4)] transition-all hover:scale-105 active:scale-95 cursor-pointer"
            >
              <Download className="w-5 h-5 stroke-[2.5]" />
              <div className="text-left leading-tight">
                <span className="block text-[10px] font-bold uppercase tracking-wider text-black/70">
                  {t.downloadSection.btnApkTitle}
                </span>
                <span>{t.downloadSection.btnApkSub}</span>
              </div>
            </button>

            {/* Google Play Button */}
            <div className="w-full sm:w-auto flex-1 inline-flex items-center justify-center gap-3 bg-slate-900/90 text-white font-bold text-base px-6 h-15 rounded-2xl border border-slate-700 shadow-md">
              <svg className="w-5 h-5 shrink-0" viewBox="0 0 24 24" fill="none">
                <path d="M3.609 1.814L13.792 12 3.61 22.186a.996.996 0 0 1-.61-.92V2.734a1 1 0 0 1 .609-.92z" fill="#4285F4"/>
                <path d="M14.499 12.707l2.302 2.302-10.937 6.333 8.635-8.635z" fill="#34A853"/>
                <path d="M16.8 9.791l-2.302 2.302L5.864 3.458 16.8 9.791z" fill="#EA4335"/>
                <path d="M20 12l-3.2 1.8-2.3-1.8 2.3-1.8L20 12z" fill="#FBBC05"/>
              </svg>
              <div className="text-left leading-tight">
                <span className="block text-[9px] font-semibold text-slate-400 uppercase tracking-wider">
                  {t.downloadSection.btnStoreTitle}
                </span>
                <span className="text-xs font-black text-slate-300">
                  {t.downloadSection.btnStoreSub}
                </span>
              </div>
            </div>

          </div>

          {/* Download Notification banner if clicked */}
          {downloadStarted && (
            <div className="mt-5 p-3.5 bg-emerald-950/90 border border-emerald-500/50 rounded-xl text-xs text-emerald-300 max-w-md mx-auto animate-in fade-in">
              {t.downloadSection.downloadSuccess}
            </div>
          )}

          {/* Quick Install Notes */}
          <div className="mt-12 pt-8 border-t border-slate-800/80 grid grid-cols-1 sm:grid-cols-3 gap-6 text-xs text-slate-400 text-left">
            <div className="flex items-start gap-3">
              <CheckCircle2 className="w-5 h-5 text-[#CCFF00] shrink-0 mt-0.5" />
              <span><strong>{t.downloadSection.tip1.title}</strong> {t.downloadSection.tip1.desc}</span>
            </div>
            <div className="flex items-start gap-3">
              <ShieldCheck className="w-5 h-5 text-[#00E5FF] shrink-0 mt-0.5" />
              <span><strong>{t.downloadSection.tip2.title}</strong> {t.downloadSection.tip2.desc}</span>
            </div>
            <div className="flex items-start gap-3">
              <Smartphone className="w-5 h-5 text-slate-300 shrink-0 mt-0.5" />
              <span><strong>{t.downloadSection.tip3.title}</strong> {t.downloadSection.tip3.desc}</span>
            </div>
          </div>

        </div>

      </div>
    </section>
  );
}
