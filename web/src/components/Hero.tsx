"use client";

import React from "react";
import { Download, Sparkles, Flame, CheckCircle, ShieldCheck, Zap } from "lucide-react";
import PhoneMockup from "./PhoneMockup";

export default function Hero() {
  return (
    <section id="demo" className="relative pt-12 pb-20 md:pt-20 md:pb-28 overflow-hidden bg-grid-pattern">
      {/* Background Ambient Spotlights */}
      <div className="absolute top-10 left-1/4 -translate-x-1/2 w-96 h-96 bg-[#CCFF00]/10 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute top-40 right-1/4 translate-x-1/2 w-96 h-96 bg-[#00E5FF]/10 rounded-full blur-3xl pointer-events-none" />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 lg:gap-8 items-center">
          
          {/* Left Hero Column: Value Proposition & CTAs */}
          <div className="lg:col-span-7 text-center lg:text-left space-y-6">
            
            {/* Top Pill Badge */}
            <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-slate-900 border border-slate-700/80 shadow-md">
              <span className="flex h-2 w-2 relative">
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-[#CCFF00] opacity-75"></span>
                <span className="relative inline-flex rounded-full h-2 w-2 bg-[#CCFF00]"></span>
              </span>
              <span className="text-xs font-bold text-slate-200">
                Amateur Padel Entertainment Engine
              </span>
              <span className="text-[10px] text-black font-extrabold bg-[#CCFF00] px-1.5 py-0.2 rounded-full uppercase">
                Free
              </span>
            </div>

            {/* Main Headline */}
            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-black tracking-tight text-white leading-[1.08]">
              Turn every match into{" "}
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#CCFF00] via-[#85ff4a] to-[#00E5FF] drop-shadow-sm">
                content worth sharing.
              </span>
            </h1>

            {/* Subhead */}
            <p className="text-lg sm:text-xl text-slate-300 max-w-2xl mx-auto lg:mx-0 leading-relaxed font-normal">
              Log your match in 10 seconds. Generate hilarious AI roast cards. Drop them straight into your WhatsApp group chat before your opponents even finish untying their shoes.
            </p>

            {/* Feature Bullets */}
            <div className="flex flex-wrap justify-center lg:justify-start gap-4 pt-1 text-xs font-semibold text-slate-300">
              <div className="flex items-center gap-1.5 bg-slate-900/60 px-3 py-1.5 rounded-lg border border-slate-800">
                <Zap className="w-4 h-4 text-[#CCFF00]" />
                <span>10-Second Flow</span>
              </div>
              <div className="flex items-center gap-1.5 bg-slate-900/60 px-3 py-1.5 rounded-lg border border-slate-800">
                <ShieldCheck className="w-4 h-4 text-[#00E5FF]" />
                <span>Only 1 person needs the app</span>
              </div>
              <div className="flex items-center gap-1.5 bg-slate-900/60 px-3 py-1.5 rounded-lg border border-slate-800">
                <Flame className="w-4 h-4 text-rose-500" />
                <span>Casual to Savage!! tones</span>
              </div>
            </div>

            {/* CTAs Box */}
            <div className="pt-4 space-y-4">
              <div className="flex flex-col sm:flex-row items-center justify-center lg:justify-start gap-4">
                
                {/* Direct Download APK CTA */}
                <a
                  href="#download"
                  className="w-full sm:w-auto inline-flex items-center justify-center gap-3 bg-[#CCFF00] hover:bg-[#b5e000] text-black font-black text-base px-8 h-14 rounded-2xl shadow-[0_0_30px_rgba(204,255,0,0.35)] transition-all hover:-translate-y-0.5 active:translate-y-0"
                >
                  <Download className="w-5 h-5 stroke-[2.5]" />
                  <div className="text-left leading-tight">
                    <span className="block text-[11px] font-bold uppercase tracking-wider text-black/75">
                      Direct Android Beta
                    </span>
                    <span>Download APK (.apk)</span>
                  </div>
                </a>

                {/* Google Play Store Button */}
                <a
                  href="#download"
                  className="w-full sm:w-auto inline-flex items-center justify-center gap-3 bg-slate-900 hover:bg-slate-800 text-white font-bold text-base px-7 h-14 rounded-2xl border border-slate-700/80 shadow-md transition-all hover:-translate-y-0.5"
                >
                  {/* Google Play SVG Icon */}
                  <svg className="w-6 h-6 shrink-0" viewBox="0 0 24 24" fill="none">
                    <path d="M3.609 1.814L13.792 12 3.61 22.186a.996.996 0 0 1-.61-.92V2.734a1 1 0 0 1 .609-.92z" fill="#4285F4"/>
                    <path d="M14.499 12.707l2.302 2.302-10.937 6.333 8.635-8.635z" fill="#34A853"/>
                    <path d="M16.8 9.791l-2.302 2.302L5.864 3.458 16.8 9.791z" fill="#EA4335"/>
                    <path d="M20 12l-3.2 1.8-2.3-1.8 2.3-1.8L20 12z" fill="#FBBC05"/>
                  </svg>
                  <div className="text-left leading-tight">
                    <span className="block text-[10px] font-semibold text-slate-400 uppercase tracking-wider">
                      Google Play
                    </span>
                    <span className="text-sm font-extrabold text-white">In Play Store Review</span>
                  </div>
                </a>
              </div>

              {/* iOS Notice */}
              <div className="flex items-center justify-center lg:justify-start gap-2 text-xs text-slate-400 font-medium pt-1">
                <span className="text-sm">🍎</span>
                <span>iOS version currently in development for TestFlight & App Store.</span>
              </div>
            </div>

            {/* Social Proof Banner */}
            <div className="pt-4 border-t border-slate-800/80 flex items-center justify-center lg:justify-start gap-6 text-xs text-slate-400">
              <div>
                <span className="font-extrabold text-white text-sm">100% Free</span> to log matches
              </div>
              <div className="h-4 w-px bg-slate-800" />
              <div>
                <span className="font-extrabold text-[#CCFF00] text-sm">Ghost Profiles</span> ready
              </div>
              <div className="h-4 w-px bg-slate-800" />
              <div>
                <span className="font-extrabold text-[#00E5FF] text-sm">WhatsApp</span> optimized
              </div>
            </div>
          </div>

          {/* Right Hero Column: Interactive Animated Phone */}
          <div className="lg:col-span-5 flex justify-center">
            <PhoneMockup />
          </div>

        </div>
      </div>
    </section>
  );
}
