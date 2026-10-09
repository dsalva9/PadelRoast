"use client";

import React, { useState } from "react";
import { Flame, Download, ExternalLink, Menu, X, Smartphone } from "lucide-react";
import { activeSport } from "@/config/sportConfig";

export default function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50 w-full border-b border-slate-800/80 bg-[#0B0F19]/85 backdrop-blur-xl transition-all">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-18 flex items-center justify-between">
        
        {/* Brand Logo */}
        <a href="#" className="flex items-center gap-2.5 group">
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-amber-500 via-rose-500 to-[#CCFF00] p-0.5 shadow-lg group-hover:scale-105 transition-transform duration-300">
            <div className="w-full h-full bg-[#0B0F19] rounded-[10px] flex items-center justify-center">
              <Flame className="w-5 h-5 text-[#CCFF00] fill-[#CCFF00]" />
            </div>
          </div>
          <div>
            <span className="text-xl font-black tracking-tight text-white">
              PADEL<span className="text-[#CCFF00]">ROAST</span>
            </span>
            <div className="flex items-center gap-1.5 -mt-1">
              <span className="text-[10px] font-bold uppercase tracking-widest text-[#00E5FF]">
                Banter Engine
              </span>
              <span className="text-[9px] px-1 rounded bg-[#CCFF00]/15 text-[#CCFF00] font-black border border-[#CCFF00]/30">
                v1.2 Beta
              </span>
            </div>
          </div>
        </a>

        {/* Desktop Nav Links */}
        <nav className="hidden md:flex items-center gap-8 text-sm font-semibold text-slate-300">
          <a href="#demo" className="hover:text-[#CCFF00] transition-colors">
            Interactive Demo
          </a>
          <a href="#tones" className="hover:text-[#CCFF00] transition-colors">
            Roast Levels
          </a>
          <a href="#ghost-profiles" className="hover:text-[#CCFF00] transition-colors">
            How It Works
          </a>
          <a href="#cards" className="hover:text-[#CCFF00] transition-colors">
            Roast Deck
          </a>
          <a href="#faq" className="hover:text-[#CCFF00] transition-colors">
            FAQ
          </a>
        </nav>

        {/* CTA Buttons */}
        <div className="hidden lg:flex items-center gap-3">
          {/* iOS Coming Soon Pill */}
          <div className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-slate-900/80 border border-slate-800 text-[11px] font-semibold text-slate-400">
            <span className="text-sm">🍎</span>
            <span>iOS Coming Soon</span>
          </div>

          {/* Download APK Button */}
          <a
            href="#download"
            className="flex items-center gap-2 bg-[#CCFF00] hover:bg-[#bceb00] text-black font-black text-xs px-4 py-2.5 rounded-xl shadow-[0_0_20px_rgba(204,255,0,0.3)] transition-all hover:scale-105 active:scale-95"
          >
            <Download className="w-4 h-4" />
            <span>Download APK</span>
          </a>
        </div>

        {/* Mobile Hamburger Button */}
        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="md:hidden p-2 text-slate-400 hover:text-white focus:outline-none"
        >
          {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
        </button>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden bg-[#0B0F19] border-b border-slate-800 px-4 pt-3 pb-6 space-y-4">
          <div className="flex flex-col space-y-3 text-sm font-semibold text-slate-300">
            <a 
              href="#demo" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              Interactive Demo
            </a>
            <a 
              href="#tones" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              Roast Levels
            </a>
            <a 
              href="#ghost-profiles" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              How It Works
            </a>
            <a 
              href="#cards" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              Roast Deck
            </a>
            <a 
              href="#faq" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              FAQ
            </a>
          </div>

          <div className="pt-2 border-t border-slate-800 space-y-2">
            <a
              href="#download"
              onClick={() => setMobileMenuOpen(false)}
              className="w-full flex items-center justify-center gap-2 bg-[#CCFF00] text-black font-black text-xs py-3 rounded-xl shadow-md"
            >
              <Download className="w-4 h-4" />
              <span>Download Android APK</span>
            </a>
            <div className="text-center text-[11px] text-slate-400 py-1">
              🍎 iOS Version Coming Soon
            </div>
          </div>
        </div>
      )}
    </header>
  );
}
