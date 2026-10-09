"use client";

import React from "react";
import { MessageSquare, CheckCheck } from "lucide-react";
import { useLanguage } from "@/context/LanguageContext";

export default function WhatsAppPreview() {
  const { t } = useLanguage();

  return (
    <section className="py-20 bg-[#0B0F19] relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        <div className="max-w-4xl mx-auto bg-gradient-to-br from-slate-900 via-slate-900 to-[#131B2E] border-2 border-slate-800 rounded-3xl p-6 sm:p-10 shadow-2xl relative overflow-hidden">
          
          <div className="text-center max-w-xl mx-auto mb-8">
            <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 text-xs font-bold uppercase tracking-wider mb-2">
              <MessageSquare className="w-3.5 h-3.5" />
              <span>{t.whatsappSection.badge}</span>
            </div>
            <h3 className="text-2xl sm:text-3xl font-black text-white">
              {t.whatsappSection.title}
            </h3>
            <p className="text-sm text-slate-300 mt-2">
              {t.whatsappSection.subtitle}
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
                    {t.whatsappSection.chatTitle}
                  </h4>
                  <p className="text-[10px] text-slate-400">
                    {t.whatsappSection.chatMembers}
                  </p>
                </div>
              </div>
              <div className="text-emerald-400 text-xs font-bold px-2 py-0.5 rounded bg-emerald-950 border border-emerald-800">
                {t.whatsappSection.online}
              </div>
            </div>

            {/* Chat Body */}
            <div className="p-4 space-y-3 bg-[radial-gradient(#1f2c34_1px,transparent_1px)] [background-size:16px_16px]">
              
              {/* Other User Message */}
              <div className="flex items-end gap-2">
                <div className="bg-[#202c33] text-slate-200 text-xs px-3 py-2 rounded-2xl rounded-bl-xs max-w-[80%] shadow-sm">
                  <span className="text-[10px] font-bold text-emerald-400 block mb-0.5">
                    {t.whatsappSection.friendMessageName}
                  </span>
                  {t.whatsappSection.friendMessageText}
                  <div className="text-[8px] text-slate-400 text-right mt-1">19:41</div>
                </div>
              </div>

              {/* PadelRoast Automated Share Drop (Outbound) */}
              <div className="flex flex-col items-end">
                <div className="bg-[#005c4b] text-white rounded-2xl rounded-br-xs p-3 max-w-[90%] shadow-md border border-emerald-500/20">
                  
                  {/* Embedded Roast Card Mini with Real Graphic */}
                  <div className="bg-[#0b141a] rounded-xl overflow-hidden border border-[#CCFF00]/40 mb-2.5">
                    <div className="relative h-32 w-full">
                      <img
                        src="/images/bagel-trophy.jpg"
                        alt="Roast Card Preview"
                        className="object-cover w-full h-full"
                      />
                      <div className="absolute top-2 left-2 bg-black/80 px-2 py-0.5 rounded text-[9px] font-black text-[#CCFF00]">
                        {t.whatsappSection.reportHeader}
                      </div>
                      <div className="absolute top-2 right-2 bg-black/80 px-2 py-0.5 rounded font-mono text-[9px] font-bold text-[#00E5FF]">
                        6-1, 6-0
                      </div>
                    </div>
                    <div className="p-2.5">
                      <p className="text-[11px] font-black text-white leading-tight">
                        {t.whatsappSection.cardTitle}
                      </p>
                      <p className="text-[9px] text-slate-300 mt-1 leading-snug">
                        {t.whatsappSection.cardDesc}
                      </p>
                    </div>
                  </div>

                  <p className="text-xs leading-snug">
                    {t.whatsappSection.officialMsg}
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
                  <span className="text-[10px] font-bold text-rose-400 block mb-0.5">
                    {t.whatsappSection.alexName}
                  </span>
                  {t.whatsappSection.alexMsg}
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
