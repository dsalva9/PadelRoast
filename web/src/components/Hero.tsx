"use client";

import React from "react";
import Image from "next/image";
import { Download, Flame, Zap, ShieldCheck, Skull, Trophy } from "lucide-react";
import PhoneMockup from "./PhoneMockup";

export default function Hero() {
  return (
    <section id="demo" className="relative pt-10 pb-20 md:pt-16 md:pb-28 overflow-hidden bg-grid-pattern">
      {/* Ambient Court Lights */}
      <div className="absolute -top-24 left-1/2 -translate-x-1/2 w-[700px] h-[350px] bg-gradient-to-b from-[#CCFF00]/15 via-[#00E5FF]/10 to-transparent blur-3xl pointer-events-none" />

      {/* Floating Streetwear Stickers (Desktop) */}
      <div className="hidden xl:block absolute top-28 left-6 rotate-[-6deg] z-20 pointer-events-none hover:rotate-0 transition-transform">
        <div className="bg-[#CCFF00] text-black font-black text-[11px] uppercase tracking-wider px-3.5 py-1.5 rounded-md shadow-[4px_4px_0px_0px_rgba(0,0,0,1)] border-2 border-black flex items-center gap-1.5">
          <span>🥯</span>
          <span>CERTIFIED BAGEL VICTIM</span>
        </div>
      </div>

      <div className="hidden xl:block absolute top-96 left-12 rotate-[4deg] z-20 pointer-events-none">
        <div className="bg-[#FF3366] text-white font-black text-[11px] uppercase tracking-wider px-3.5 py-1.5 rounded-md shadow-[4px_4px_0px_0px_rgba(0,0,0,1)] border-2 border-black flex items-center gap-1.5">
          <Flame className="w-3.5 h-3.5 fill-white" />
          <span>€380 RACKET • 0 ACES</span>
        </div>
      </div>

      <div className="hidden xl:block absolute top-36 right-8 rotate-[5deg] z-20 pointer-events-none">
        <div className="bg-[#00E5FF] text-black font-black text-[11px] uppercase tracking-wider px-3 py-1.5 rounded-md shadow-[4px_4px_0px_0px_rgba(0,0,0,1)] border-2 border-black flex items-center gap-1.5">
          <Skull className="w-3.5 h-3.5" />
          <span>WHATSAPP CHAT DESTROYER</span>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-10 lg:gap-8 items-center">
          
          {/* Left Column: Bold Sports Editorial Copy */}
          <div className="lg:col-span-7 text-center lg:text-left space-y-6">
            
            {/* Top Badge: Tape style */}
            <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-lg bg-black/80 border-2 border-[#CCFF00]/40 text-[#CCFF00] shadow-[0_0_15px_rgba(204,255,0,0.2)]">
              <span className="flex h-2 w-2 relative">
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-[#CCFF00] opacity-75"></span>
                <span className="relative inline-flex rounded-full h-2 w-2 bg-[#CCFF00]"></span>
              </span>
              <span className="text-xs font-black uppercase tracking-wider">
                Amateur Padel Roast Engine
              </span>
              <span className="text-[10px] text-black font-black bg-[#CCFF00] px-1.5 py-0.5 rounded uppercase">
                v1.2 Beta
              </span>
            </div>

            {/* Main Headline: Punchy, unapologetic */}
            <h1 className="text-4xl sm:text-6xl font-black tracking-tight text-white uppercase leading-[1.02]">
              You played like crap.{" "}
              <span className="block text-transparent bg-clip-text bg-gradient-to-r from-[#CCFF00] via-[#85ff4a] to-[#00E5FF]">
                Your group chat deserves to know.
              </span>
            </h1>

            {/* Subhead with real human padel attitude */}
            <p className="text-base sm:text-lg text-slate-300 max-w-xl mx-auto lg:mx-0 leading-relaxed font-medium">
              Log match scores in 10 seconds. Generate brutal AI roast cards that target €300 carbon rackets, fake calf injuries, and tragic missed bandejas. Drop them straight into WhatsApp before the beers arrive.
            </p>

            {/* Feature Pills */}
            <div className="flex flex-wrap justify-center lg:justify-start gap-2.5 pt-1 text-xs font-extrabold">
              <div className="flex items-center gap-1.5 bg-slate-900 border border-slate-700/80 px-3 py-1.5 rounded-xl text-slate-200">
                <Zap className="w-3.5 h-3.5 text-[#CCFF00]" />
                <span>10-Second Post-Match Flow</span>
              </div>
              <div className="flex items-center gap-1.5 bg-slate-900 border border-slate-700/80 px-3 py-1.5 rounded-xl text-slate-200">
                <ShieldCheck className="w-3.5 h-3.5 text-[#00E5FF]" />
                <span>Only 1 person needs the app</span>
              </div>
              <div className="flex items-center gap-1.5 bg-slate-900 border border-slate-700/80 px-3 py-1.5 rounded-xl text-slate-200">
                <Flame className="w-3.5 h-3.5 text-[#FF3366]" />
                <span>Casual to Savage!! tones</span>
              </div>
            </div>

            {/* CTAs */}
            <div className="pt-3 space-y-3.5">
              <div className="flex flex-col sm:flex-row items-center justify-center lg:justify-start gap-3.5">
                
                {/* Direct Download APK CTA */}
                <a
                  href="#download"
                  className="w-full sm:w-auto inline-flex items-center justify-center gap-3 bg-[#CCFF00] hover:bg-[#b5e000] text-black font-black text-base px-8 h-14 rounded-2xl shadow-[0_0_30px_rgba(204,255,0,0.35)] transition-all hover:-translate-y-0.5 active:translate-y-0"
                >
                  <Download className="w-5 h-5 stroke-[2.5]" />
                  <div className="text-left leading-tight">
                    <span className="block text-[10px] font-bold uppercase tracking-wider text-black/75">
                      Direct Android Beta
                    </span>
                    <span>Download APK (.apk)</span>
                  </div>
                </a>

                {/* Google Play Button */}
                <a
                  href="#download"
                  className="w-full sm:w-auto inline-flex items-center justify-center gap-3 bg-slate-900 hover:bg-slate-800 text-white font-bold text-base px-6 h-14 rounded-2xl border border-slate-700/80 shadow-md transition-all hover:-translate-y-0.5"
                >
                  <svg className="w-5 h-5 shrink-0" viewBox="0 0 24 24" fill="none">
                    <path d="M3.609 1.814L13.792 12 3.61 22.186a.996.996 0 0 1-.61-.92V2.734a1 1 0 0 1 .609-.92z" fill="#4285F4"/>
                    <path d="M14.499 12.707l2.302 2.302-10.937 6.333 8.635-8.635z" fill="#34A853"/>
                    <path d="M16.8 9.791l-2.302 2.302L5.864 3.458 16.8 9.791z" fill="#EA4335"/>
                    <path d="M20 12l-3.2 1.8-2.3-1.8 2.3-1.8L20 12z" fill="#FBBC05"/>
                  </svg>
                  <div className="text-left leading-tight">
                    <span className="block text-[9px] font-semibold text-slate-400 uppercase tracking-wider">
                      Google Play
                    </span>
                    <span className="text-xs font-black text-white">Publishing Soon</span>
                  </div>
                </a>
              </div>

              {/* iOS Notice */}
              <div className="flex items-center justify-center lg:justify-start gap-2 text-xs text-slate-400 font-medium">
                <span className="text-sm">🍎</span>
                <span>iOS version preparing for TestFlight & App Store.</span>
              </div>
            </div>

            {/* Quick Proof Strip */}
            <div className="pt-4 border-t border-slate-800/80 flex items-center justify-center lg:justify-start gap-6 text-xs text-slate-400 font-medium">
              <div>
                <strong className="text-white">100% Free</strong> to roast matches
              </div>
              <div className="h-3 w-px bg-slate-800" />
              <div>
                <strong className="text-[#CCFF00]">Ghost Profiles</strong> included
              </div>
              <div className="h-3 w-px bg-slate-800" />
              <div>
                <strong className="text-[#00E5FF]">1-Tap WhatsApp</strong> ready
              </div>
            </div>

          </div>

          {/* Right Column: Phone Mockup with Graphic Frame Accent */}
          <div className="lg:col-span-5 flex flex-col items-center relative">
            <PhoneMockup />

            {/* Subtle graphic card teaser below phone */}
            <div className="mt-4 flex items-center gap-3 p-2.5 bg-slate-900/90 border border-slate-800 rounded-2xl shadow-xl max-w-xs">
              <div className="w-10 h-10 rounded-xl overflow-hidden relative shrink-0 border border-[#CCFF00]/40">
                <Image
                  src="/images/padel-racket-fire.jpg"
                  alt="Burning Padel Racket"
                  fill
                  className="object-cover"
                />
              </div>
              <div className="text-left">
                <div className="text-[10px] font-black uppercase text-[#CCFF00] tracking-wide">
                  Real AI Match Burns
                </div>
                <div className="text-[11px] text-slate-300 font-semibold leading-tight">
                  Generates custom jokes based on actual set scores.
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </section>
  );
}
