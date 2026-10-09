"use client";

import React from "react";
import { Users, XCircle, CheckCircle2, MessageSquare, Zap, ShieldAlert } from "lucide-react";

export default function GhostProfileFeature() {
  return (
    <section id="ghost-profiles" className="py-24 bg-[#0B0F19] relative overflow-hidden">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-[#00E5FF]/15 border border-[#00E5FF]/40 text-[#00E5FF] text-xs font-black uppercase tracking-wider mb-3">
            <Users className="w-3.5 h-3.5" />
            <span>Zero Friction Technology</span>
          </div>
          <h2 className="text-3xl sm:text-5xl font-black text-white tracking-tight uppercase">
            Only ONE person needs the app.{" "}
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-[#00E5FF] to-[#CCFF00]">
              Seriously.
            </span>
          </h2>
          <p className="mt-4 text-base sm:text-lg text-slate-300 font-medium">
            We all have that friend who refuses to download anything new. With Ghost Profiles, nobody has to sign up to get roasted.
          </p>
        </div>

        {/* Side-by-side Comparison Grid: Brutally honest */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-5xl mx-auto">
          
          {/* Other Apps: Frustrating */}
          <div className="bg-rose-950/20 border-2 border-rose-900/60 rounded-3xl p-8 shadow-xl flex flex-col justify-between">
            <div>
              <div className="flex items-center justify-between mb-6">
                <span className="text-xs font-black uppercase tracking-wider px-3 py-1 rounded-full bg-rose-500/20 text-rose-400 border border-rose-500/30">
                  Every Other Padel App 😩
                </span>
                <XCircle className="w-6 h-6 text-rose-500" />
              </div>

              <h3 className="text-xl font-black text-white mb-4">
                The 20-Minute Registration Hostage Situation
              </h3>

              <div className="space-y-3.5 text-xs sm:text-sm text-slate-300">
                <div className="flex items-start gap-2.5">
                  <span className="text-rose-400 font-bold shrink-0">✕</span>
                  <span>Requires all 4 players to download the app from Google Play/App Store.</span>
                </div>
                <div className="flex items-start gap-2.5">
                  <span className="text-rose-400 font-bold shrink-0">✕</span>
                  <span>Friend forgets password, OTP email lands in spam folder.</span>
                </div>
                <div className="flex items-start gap-2.5">
                  <span className="text-rose-400 font-bold shrink-0">✕</span>
                  <span>Requests phone contacts, location tracking, and credit cards.</span>
                </div>
                <div className="flex items-start gap-2.5">
                  <span className="text-rose-400 font-bold shrink-0">✕</span>
                  <span>Everyone gets annoyed and sends a blurry score photo in WhatsApp anyway.</span>
                </div>
              </div>
            </div>

            <div className="mt-8 pt-4 border-t border-rose-900/40 text-xs text-rose-300/80 italic font-medium">
              Result: 0 fun, dead social feature.
            </div>
          </div>

          {/* PadelRoast: Frictionless & Fun */}
          <div className="bg-gradient-to-b from-slate-900 to-[#0F172A] border-2 border-[#CCFF00] rounded-3xl p-8 shadow-[0_0_35px_rgba(204,255,0,0.15)] flex flex-col justify-between relative">
            <div className="absolute -top-3.5 -right-3.5 bg-[#CCFF00] text-black font-black text-xs uppercase px-3 py-1 rounded-full shadow-md border-2 border-black rotate-3">
              The PadelRoast Way ⚡
            </div>

            <div>
              <div className="flex items-center justify-between mb-6">
                <span className="text-xs font-black uppercase tracking-wider px-3 py-1 rounded-full bg-[#CCFF00]/20 text-[#CCFF00] border border-[#CCFF00]/40">
                  Ghost Profiles System 🔥
                </span>
                <CheckCircle2 className="w-6 h-6 text-[#CCFF00]" />
              </div>

              <h3 className="text-xl font-black text-white mb-4">
                10 Seconds. 1 Phone. Group Chat Explosion.
              </h3>

              <div className="space-y-3.5 text-xs sm:text-sm text-slate-200">
                <div className="flex items-start gap-2.5">
                  <span className="text-[#CCFF00] font-black shrink-0">✓</span>
                  <span><strong>Only YOU need the app:</strong> Just type your 3 friends&apos; first names.</span>
                </div>
                <div className="flex items-start gap-2.5">
                  <span className="text-[#CCFF00] font-black shrink-0">✓</span>
                  <span><strong>Zero Signups:</strong> No invites, no emails, no SMS verification codes.</span>
                </div>
                <div className="flex items-start gap-2.5">
                  <span className="text-[#CCFF00] font-black shrink-0">✓</span>
                  <span><strong>Ghost Profiles track history:</strong> Elo ratings and titles accumulate automatically.</span>
                </div>
                <div className="flex items-start gap-2.5">
                  <span className="text-[#CCFF00] font-black shrink-0">✓</span>
                  <span><strong>Claim Anytime:</strong> If your friend eventually installs the app, they can claim their profile with 1 tap.</span>
                </div>
              </div>
            </div>

            <div className="mt-8 pt-4 border-t border-slate-800 text-xs text-[#CCFF00] font-black uppercase tracking-wider">
              Result: 15 seconds to viral WhatsApp comedy.
            </div>
          </div>

        </div>

      </div>
    </section>
  );
}
