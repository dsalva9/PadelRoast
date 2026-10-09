"use client";

import React, { useState } from "react";
import { Download, CheckCircle2, AlertCircle, Smartphone, ShieldCheck, Sparkles, ExternalLink } from "lucide-react";

export default function DownloadCTA() {
  const [downloadStarted, setDownloadStarted] = useState(false);

  const handleApkDownload = () => {
    setDownloadStarted(true);
    // In production, triggers the download of the actual APK file
    const link = document.createElement("a");
    link.href = "/apk/padelroast-beta.apk";
    link.download = "padelroast-beta.apk";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <section id="download" className="py-20 relative bg-slate-950 border-t border-slate-800/80 overflow-hidden">
      
      {/* Background Lighting */}
      <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-full max-w-4xl h-80 bg-gradient-to-r from-[#CCFF00]/15 via-rose-500/10 to-[#00E5FF]/15 blur-3xl pointer-events-none" />

      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        
        <div className="bg-gradient-to-b from-slate-900 via-slate-900 to-[#0B0F19] border-2 border-slate-700/80 rounded-3xl p-8 sm:p-12 text-center shadow-[0_0_50px_rgba(0,0,0,0.8)]">
          
          <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-[#CCFF00]/15 border border-[#CCFF00]/40 text-[#CCFF00] text-xs font-black uppercase tracking-wider mb-6">
            <Sparkles className="w-4 h-4" />
            <span>Ready for Match Day</span>
          </div>

          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight max-w-2xl mx-auto leading-tight">
            Stop sending boring score screenshots.
          </h2>

          <p className="mt-4 text-base sm:text-lg text-slate-300 max-w-xl mx-auto">
            Get PadelRoast right now. Install the Android beta directly or follow our Google Play release.
          </p>

          {/* Download Buttons Group */}
          <div className="mt-8 flex flex-col sm:flex-row items-center justify-center gap-4 max-w-md mx-auto">
            
            {/* Direct APK Button */}
            <button
              onClick={handleApkDownload}
              className="w-full sm:w-auto flex-1 inline-flex items-center justify-center gap-3 bg-[#CCFF00] hover:bg-[#b8e600] text-black font-black text-base px-6 h-14 rounded-2xl shadow-[0_0_30px_rgba(204,255,0,0.4)] transition-all hover:scale-105 active:scale-95 cursor-pointer"
            >
              <Download className="w-5 h-5 stroke-[2.5]" />
              <div className="text-left leading-tight">
                <span className="block text-[10px] font-bold uppercase tracking-wider text-black/70">
                  Android Beta (v1.2)
                </span>
                <span>Download APK</span>
              </div>
            </button>

            {/* Google Play Button */}
            <div className="w-full sm:w-auto flex-1 inline-flex items-center justify-center gap-3 bg-slate-900/90 text-white font-bold text-base px-6 h-14 rounded-2xl border border-slate-700 shadow-md">
              <svg className="w-5 h-5 shrink-0" viewBox="0 0 24 24" fill="none">
                <path d="M3.609 1.814L13.792 12 3.61 22.186a.996.996 0 0 1-.61-.92V2.734a1 1 0 0 1 .609-.92z" fill="#4285F4"/>
                <path d="M14.499 12.707l2.302 2.302-10.937 6.333 8.635-8.635z" fill="#34A853"/>
                <path d="M16.8 9.791l-2.302 2.302L5.864 3.458 16.8 9.791z" fill="#EA4335"/>
                <path d="M20 12l-3.2 1.8-2.3-1.8 2.3-1.8L20 12z" fill="#FBBC05"/>
              </svg>
              <div className="text-left leading-tight">
                <span className="block text-[9px] font-semibold text-slate-400 uppercase tracking-wider">
                  Google Play Store
                </span>
                <span className="text-xs font-black text-slate-300">Publishing Soon</span>
              </div>
            </div>

          </div>

          {/* Download Notification banner if clicked */}
          {downloadStarted && (
            <div className="mt-4 p-3 bg-emerald-950/80 border border-emerald-500/40 rounded-xl text-xs text-emerald-300 max-w-md mx-auto animate-in fade-in">
              ✓ Downloading <strong>padelroast-beta.apk</strong>! Open the file on your Android phone and tap <em>Install</em>.
            </div>
          )}

          {/* Quick Installation Tips */}
          <div className="mt-10 pt-8 border-t border-slate-800/80 grid grid-cols-1 sm:grid-cols-3 gap-4 text-xs text-slate-400 text-left">
            <div className="flex items-start gap-2.5">
              <CheckCircle2 className="w-4 h-4 text-[#CCFF00] shrink-0 mt-0.5" />
              <span><strong>Fast 10-Second Setup:</strong> No mandatory sign-up to start roasting your first match.</span>
            </div>
            <div className="flex items-start gap-2.5">
              <ShieldCheck className="w-4 h-4 text-[#00E5FF] shrink-0 mt-0.5" />
              <span><strong>Verified APK Build:</strong> Signed directly by the PadelRoast development team.</span>
            </div>
            <div className="flex items-start gap-2.5">
              <Smartphone className="w-4 h-4 text-slate-300 shrink-0 mt-0.5" />
              <span><strong>iOS Waitlist:</strong> TestFlight and App Store build launching shortly.</span>
            </div>
          </div>

        </div>

      </div>
    </section>
  );
}
