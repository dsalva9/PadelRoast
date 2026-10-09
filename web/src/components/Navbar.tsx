"use client";

import React, { useState } from "react";
import { Flame, Download, Menu, X } from "lucide-react";
import { useLanguage } from "@/context/LanguageContext";
import LanguageSelector from "./LanguageSelector";

export default function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const { t } = useLanguage();

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
        <nav className="hidden md:flex items-center gap-7 text-sm font-semibold text-slate-300">
          <a href="#demo" className="hover:text-[#CCFF00] transition-colors">
            {t.nav.demo}
          </a>
          <a href="#tones" className="hover:text-[#CCFF00] transition-colors">
            {t.nav.tones}
          </a>
          <a href="#ghost-profiles" className="hover:text-[#CCFF00] transition-colors">
            {t.nav.howItWorks}
          </a>
          <a href="#cards" className="hover:text-[#CCFF00] transition-colors">
            {t.nav.roastDeck}
          </a>
          <a href="#faq" className="hover:text-[#CCFF00] transition-colors">
            {t.nav.faq}
          </a>
        </nav>

        {/* Right Action Controls */}
        <div className="hidden lg:flex items-center gap-3">
          {/* Language Selector Dropdown */}
          <LanguageSelector />

          {/* iOS Coming Soon Pill */}
          <div className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-slate-900/80 border border-slate-800 text-[11px] font-semibold text-slate-400">
            <span className="text-sm">🍎</span>
            <span>{t.nav.iosComingSoon}</span>
          </div>

          {/* Download APK Button */}
          <a
            href="#download"
            className="flex items-center gap-2 bg-[#CCFF00] hover:bg-[#bceb00] text-black font-black text-xs px-4 py-2.5 rounded-xl shadow-[0_0_20px_rgba(204,255,0,0.3)] transition-all hover:scale-105 active:scale-95 cursor-pointer"
          >
            <Download className="w-4 h-4" />
            <span>{t.nav.downloadApk}</span>
          </a>
        </div>

        {/* Mobile Header Controls */}
        <div className="flex items-center gap-2 lg:hidden">
          <LanguageSelector compact />
          <button
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            className="p-2 text-slate-400 hover:text-white focus:outline-none"
            aria-label="Toggle navigation menu"
          >
            {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="lg:hidden bg-[#0B0F19] border-b border-slate-800 px-4 pt-3 pb-6 space-y-4">
          <div className="flex flex-col space-y-3 text-sm font-semibold text-slate-300">
            <a 
              href="#demo" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              {t.nav.demo}
            </a>
            <a 
              href="#tones" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              {t.nav.tones}
            </a>
            <a 
              href="#ghost-profiles" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              {t.nav.howItWorks}
            </a>
            <a 
              href="#cards" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              {t.nav.roastDeck}
            </a>
            <a 
              href="#faq" 
              onClick={() => setMobileMenuOpen(false)}
              className="py-1 hover:text-[#CCFF00]"
            >
              {t.nav.faq}
            </a>
          </div>

          <div className="pt-3 border-t border-slate-800 space-y-2.5">
            <a
              href="#download"
              onClick={() => setMobileMenuOpen(false)}
              className="w-full flex items-center justify-center gap-2 bg-[#CCFF00] text-black font-black text-xs py-3 rounded-xl shadow-md cursor-pointer"
            >
              <Download className="w-4 h-4" />
              <span>{t.nav.downloadApk}</span>
            </a>
            <div className="text-center text-[11px] text-slate-400 py-1">
              🍎 {t.nav.iosComingSoon}
            </div>
          </div>
        </div>
      )}
    </header>
  );
}
