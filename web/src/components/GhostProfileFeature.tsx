"use client";

import React from "react";
import { UserCheck, Users, ShieldAlert, Zap, ArrowRight, Share2, MessageSquare } from "lucide-react";

export default function GhostProfileFeature() {
  return (
    <section id="ghost-profiles" className="py-20 bg-[#0B0F19] relative overflow-hidden">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-center">
          
          {/* Left Column: Visual Diagram */}
          <div className="lg:col-span-6 order-2 lg:order-1">
            <div className="bg-slate-900/90 border border-slate-800 rounded-3xl p-6 sm:p-8 shadow-2xl relative">
              <div className="absolute top-0 right-0 transform translate-x-2 -translate-y-2 bg-[#CCFF00] text-black text-[10px] font-black uppercase px-3 py-1 rounded-full shadow-md">
                Zero Friction
              </div>

              <h4 className="text-xs font-bold uppercase tracking-wider text-[#00E5FF] mb-4">
                The Frictionless Viral Loop
              </h4>

              {/* Step 1: Organizer */}
              <div className="flex items-start gap-4 p-4 rounded-2xl bg-slate-950 border border-slate-800 mb-3">
                <div className="w-10 h-10 rounded-xl bg-[#CCFF00] text-black font-black flex items-center justify-center shrink-0 text-sm">
                  YOU
                </div>
                <div>
                  <div className="font-extrabold text-sm text-white flex items-center gap-2">
                    <span>You log the match</span>
                    <span className="text-[10px] bg-emerald-500/20 text-emerald-400 font-bold px-2 py-0.5 rounded">
                      Only 1 App Needed
                    </span>
                  </div>
                  <p className="text-xs text-slate-400 mt-1">
                    Enter the score and names. No invites or account requests sent to anyone.
                  </p>
                </div>
              </div>

              {/* Arrow */}
              <div className="flex justify-center my-1 text-slate-600">
                ↓
              </div>

              {/* Step 2: Ghost Profiles */}
              <div className="flex items-start gap-4 p-4 rounded-2xl bg-slate-950 border border-slate-800 mb-3">
                <div className="w-10 h-10 rounded-xl bg-slate-800 text-slate-300 font-bold flex items-center justify-center shrink-0 text-sm">
                  👻
                </div>
                <div>
                  <div className="font-extrabold text-sm text-white">
                    Ghost Profiles Auto-Created
                  </div>
                  <p className="text-xs text-slate-400 mt-1">
                    Carlos, David, Alex, and Marcos get instant Elo tracking & match history without creating passwords.
                  </p>
                </div>
              </div>

              {/* Arrow */}
              <div className="flex justify-center my-1 text-slate-600">
                ↓
              </div>

              {/* Step 3: WhatsApp Explosion */}
              <div className="flex items-start gap-4 p-4 rounded-2xl bg-[#0b141a] border border-emerald-900/60">
                <div className="w-10 h-10 rounded-xl bg-emerald-600 text-white font-black flex items-center justify-center shrink-0 text-sm">
                  <MessageSquare className="w-5 h-5" />
                </div>
                <div>
                  <div className="font-extrabold text-sm text-white flex items-center gap-2">
                    <span>Dropped into WhatsApp</span>
                    <span className="text-[10px] bg-rose-500/20 text-rose-400 font-bold px-2 py-0.5 rounded">
                      Instant Chaos
                    </span>
                  </div>
                  <p className="text-xs text-slate-300 mt-1">
                    Cards and recaps get shared. The group chat erupts into banter and rematch challenges.
                  </p>
                </div>
              </div>

            </div>
          </div>

          {/* Right Column: Copy & Details */}
          <div className="lg:col-span-6 order-1 lg:order-2 space-y-6">
            <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-[#00E5FF]/10 border border-[#00E5FF]/30 text-[#00E5FF] text-xs font-bold uppercase tracking-wider">
              <Users className="w-3.5 h-3.5" />
              <span>Ghost Profiles & Claim System</span>
            </div>

            <h2 className="text-3xl sm:text-4xl font-black text-white tracking-tight leading-tight">
              Don&apos;t force your friends to download another app.
            </h2>

            <p className="text-base sm:text-lg text-slate-300 leading-relaxed">
              We all know that one friend who refuses to install anything new. With PadelRoast, they don&apos;t have to.
            </p>

            <div className="space-y-4 pt-2">
              <div className="flex items-start gap-3">
                <div className="w-6 h-6 rounded-full bg-[#CCFF00]/20 text-[#CCFF00] flex items-center justify-center shrink-0 mt-0.5 font-black text-xs">
                  ✓
                </div>
                <div>
                  <h4 className="text-sm font-bold text-white">Instant Name Reuse</h4>
                  <p className="text-xs text-slate-400 mt-0.5">
                    After your first match, your regular playing partners are saved locally for 1-tap selection next time.
                  </p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <div className="w-6 h-6 rounded-full bg-[#CCFF00]/20 text-[#CCFF00] flex items-center justify-center shrink-0 mt-0.5 font-black text-xs">
                  ✓
                </div>
                <div>
                  <h4 className="text-sm font-bold text-white">Personalized Player Titles</h4>
                  <p className="text-xs text-slate-400 mt-0.5">
                    The AI assigns titles like <span className="text-[#CCFF00] font-semibold">&quot;Fence Smasher&quot;</span>, <span className="text-[#00E5FF] font-semibold">&quot;Baseline Hugger&quot;</span>, or <span className="text-rose-400 font-semibold">&quot;The Lob Addict&quot;</span> based on match stats.
                  </p>
                </div>
              </div>

              <div className="flex items-start gap-3">
                <div className="w-6 h-6 rounded-full bg-[#CCFF00]/20 text-[#CCFF00] flex items-center justify-center shrink-0 mt-0.5 font-black text-xs">
                  ✓
                </div>
                <div>
                  <h4 className="text-sm font-bold text-white">Claim Anytime</h4>
                  <p className="text-xs text-slate-400 mt-0.5">
                    When your friends eventually install PadelRoast, they can claim their Ghost Profile to instantly inherit their complete match record.
                  </p>
                </div>
              </div>
            </div>

          </div>

        </div>

      </div>
    </section>
  );
}
