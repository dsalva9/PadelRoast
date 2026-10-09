"use client";

import React from "react";
import { activeSport } from "@/config/sportConfig";
import { Flame } from "lucide-react";

export default function Footer() {
  return (
    <footer className="bg-slate-950 border-t border-slate-800/80 py-14 text-slate-400 text-xs">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 space-y-10">
        
        <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-6 pb-8 border-b border-slate-800/60">
          
          {/* Logo & Tagline */}
          <div className="space-y-1.5">
            <div className="flex items-center gap-2">
              <div className="w-8 h-8 rounded-lg bg-gradient-to-tr from-amber-500 to-[#CCFF00] flex items-center justify-center text-sm shadow">
                🔥
              </div>
              <span className="text-lg font-black text-white tracking-tight">
                PADEL<span className="text-[#CCFF00]">ROAST</span>
              </span>
            </div>
            <p className="text-xs text-slate-400">
              Turn every match into content worth sharing.
            </p>
          </div>

          {/* Quick jump */}
          <div className="flex flex-wrap gap-6 text-xs font-semibold text-slate-300">
            <a href="#demo" className="hover:text-[#CCFF00] transition-colors">Interactive Demo</a>
            <a href="#tones" className="hover:text-[#CCFF00] transition-colors">Roast Levels</a>
            <a href="#ghost-profiles" className="hover:text-[#CCFF00] transition-colors">Ghost Profiles</a>
            <a href="#download" className="hover:text-[#CCFF00] transition-colors">Download APK</a>
            <a href="#faq" className="hover:text-[#CCFF00] transition-colors">FAQ</a>
          </div>

        </div>

        {/* Sports Franchise Network Banner */}
        <div className="space-y-3">
          <div className="text-[11px] uppercase font-bold tracking-wider text-slate-400">
            The Sports Banter Network
          </div>

          <div className="flex flex-wrap gap-2.5">
            {activeSport.sequels.map((seq, idx) => (
              <div
                key={idx}
                className={`flex items-center gap-1.5 px-3 py-1.5 rounded-xl border text-xs font-bold transition-all ${
                  seq.status === "live"
                    ? "bg-[#CCFF00]/10 border-[#CCFF00]/40 text-[#CCFF00]"
                    : "bg-slate-900 border-slate-800 text-slate-400 hover:text-slate-200"
                }`}
              >
                <span>{seq.icon}</span>
                <span>{seq.name}</span>
                <span className="text-[9px] uppercase px-1 rounded font-extrabold bg-slate-800 text-slate-400">
                  {seq.status === "live" ? "Live" : "Soon"}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* Bottom copyright & legal */}
        <div className="pt-6 border-t border-slate-900 flex flex-col sm:flex-row items-center justify-between gap-4 text-slate-400 text-[11px]">
          <p>© 2026 PadelRoast. All rights reserved. Built for amateur padel lovers.</p>
          <p className="italic">PadelRoast is designed for good-natured comedy & post-match group chat entertainment.</p>
        </div>

      </div>
    </footer>
  );
}
