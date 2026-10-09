"use client";

import React from "react";
import { MessageSquare, Share2, CheckCheck, Smile, Flame } from "lucide-react";

export default function WhatsAppPreview() {
  return (
    <section className="py-20 bg-[#0B0F19] relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <div className="max-w-4xl mx-auto bg-gradient-to-br from-slate-900 via-slate-900 to-[#131B2E] border-2 border-slate-800 rounded-3xl p-6 sm:p-10 shadow-2xl relative overflow-hidden">
          
          <div className="text-center max-w-xl mx-auto mb-8">
            <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 text-xs font-bold uppercase tracking-wider mb-2">
              <MessageSquare className="w-3.5 h-3.5" />
              <span>1-Tap WhatsApp Integration</span>
            </div>
            <h3 className="text-2xl sm:text-3xl font-black text-white">
              Instant Group Chat Fireworks
            </h3>
            <p className="text-sm text-slate-300 mt-2">
              No manual typing. One tap formats the score, players, AI narrative, and card graphic ready to send.
            </p>
          </div>

          {/* WhatsApp Chat Container */}
          <div className="max-w-lg mx-auto bg-[#0b141a] rounded-2xl border border-emerald-950 shadow-2xl overflow-hidden">
            
            {/* WhatsApp Header */}
            <div className="bg-[#1f2c34] px-4 py-3 flex items-center justify-between border-b border-emerald-950">
              <div className="flex items-center gap-3">
                <div className="w-9 h-9 rounded-full bg-emerald-600 text-white font-black flex items-center justify-center text-sm shadow">
                  🎾
                </div>
                <div>
                  <h4 className="text-xs font-bold text-white leading-tight">
                    Padel & Cervezas 🍺
                  </h4>
                  <p className="text-[10px] text-slate-400">
                    Carlos, David, Alex, Marcos
                  </p>
                </div>
              </div>
              <div className="text-emerald-400 text-xs font-bold px-2 py-0.5 rounded bg-emerald-950 border border-emerald-800">
                Online
              </div>
            </div>

            {/* Chat Body */}
            <div className="p-4 space-y-3 bg-[radial-gradient(#1f2c34_1px,transparent_1px)] [background-size:16px_16px]">
              
              {/* Other User Message */}
              <div className="flex items-end gap-2">
                <div className="bg-[#202c33] text-slate-200 text-xs px-3 py-2 rounded-2xl rounded-bl-xs max-w-[80%] shadow-sm">
                  <span className="text-[10px] font-bold text-emerald-400 block mb-0.5">David</span>
                  Great match today guys! That 2nd set was super close right?
                  <div className="text-[8px] text-slate-400 text-right mt-1">19:41</div>
                </div>
              </div>

              {/* PadelRoast Automated Share Drop (Outbound) */}
              <div className="flex flex-col items-end">
                <div className="bg-[#005c4b] text-white rounded-2xl rounded-br-xs p-3 max-w-[90%] shadow-md border border-emerald-500/20">
                  
                  {/* Embedded Roast Card Mini */}
                  <div className="bg-[#0b141a] rounded-xl p-3 border border-[#CCFF00]/40 mb-2">
                    <div className="flex justify-between items-center text-[10px] mb-1">
                      <span className="font-extrabold text-[#CCFF00]">🔥 PADELROAST REPORT</span>
                      <span className="font-mono font-bold text-[#00E5FF]">6-1, 6-0</span>
                    </div>
                    <p className="text-[11px] font-bold text-white leading-tight">
                      &ldquo;A Formal Investigation Launched on Court 3&rdquo;
                    </p>
                    <p className="text-[9px] text-slate-300 mt-1 leading-snug">
                      David and Alex suffered an unprecedented technical collapse. David&apos;s €340 carbon racket has filed for emotional emancipation.
                    </p>
                  </div>

                  <p className="text-xs leading-snug">
                    🚨 Official result from Court 3: Carlos & Marcos take the victory 6-1 6-0! Rest in peace boys, post-match round of beers is on you 🍻💀
                  </p>

                  <div className="flex items-center justify-end gap-1 text-[8px] text-emerald-200 mt-1.5">
                    <span>19:42</span>
                    <CheckCheck className="w-3.5 h-3.5 text-[#00E5FF]" />
                  </div>
                </div>

                {/* Reaction Pill */}
                <div className="flex items-center gap-1.5 bg-[#202c33] border border-slate-700 px-2 py-0.5 rounded-full text-xs -mt-2 mr-3 shadow-md z-10">
                  <span>😂 4</span>
                  <span>💀 3</span>
                  <span>🍺 2</span>
                </div>
              </div>

              {/* Alex Reaction */}
              <div className="flex items-end gap-2 pt-1">
                <div className="bg-[#202c33] text-slate-200 text-xs px-3 py-2 rounded-2xl rounded-bl-xs max-w-[80%] shadow-sm">
                  <span className="text-[10px] font-bold text-rose-400 block mb-0.5">Alex</span>
                  I hate this app so much 😭😭 Rematch next Tuesday, we are NOT ending like this!!
                  <div className="text-[8px] text-slate-400 text-right mt-1">19:43</div>
                </div>
              </div>

            </div>

          </div>

        </div>

      </div>
    </section>
  );
}
