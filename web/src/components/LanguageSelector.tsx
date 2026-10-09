"use client";

import React, { useState, useRef, useEffect } from "react";
import { useLanguage } from "@/context/LanguageContext";
import { Language, languages } from "@/translations";
import { ChevronDown, Globe, Check } from "lucide-react";

interface LanguageSelectorProps {
  compact?: boolean;
}

export default function LanguageSelector({ compact = false }: LanguageSelectorProps) {
  const { language, setLanguage } = useLanguage();
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  // Close dropdown when clicking outside
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const currentLang = languages[language] || languages.en;

  const handleSelect = (langKey: Language) => {
    setLanguage(langKey);
    setIsOpen(false);
  };

  return (
    <div className="relative inline-block text-left" ref={dropdownRef}>
      {/* Trigger Button */}
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="flex items-center gap-1.5 px-2.5 py-1.5 rounded-xl bg-slate-900/90 hover:bg-slate-800 text-slate-200 border border-slate-700/80 hover:border-[#CCFF00]/50 transition-all text-xs font-bold shadow-sm active:scale-95 cursor-pointer"
        aria-label="Select language"
      >
        <span className="text-base leading-none">{currentLang.flag}</span>
        {!compact && <span className="hidden sm:inline text-xs">{currentLang.name}</span>}
        <span className="uppercase text-[11px] font-black text-[#CCFF00] tracking-wider">
          {language}
        </span>
        <ChevronDown
          className={`w-3.5 h-3.5 text-slate-400 transition-transform duration-200 ${
            isOpen ? "rotate-180 text-[#CCFF00]" : ""
          }`}
        />
      </button>

      {/* Dropdown Menu */}
      {isOpen && (
        <div className="absolute right-0 mt-2 w-48 rounded-2xl bg-slate-900 border-2 border-slate-700/90 shadow-2xl p-1.5 z-50 animate-in fade-in zoom-in-95 duration-150 backdrop-blur-xl">
          <div className="px-2.5 py-1.5 text-[10px] font-black uppercase tracking-wider text-slate-400 border-b border-slate-800 mb-1 flex items-center gap-1.5">
            <Globe className="w-3 h-3 text-[#00E5FF]" />
            <span>Select Language</span>
          </div>

          <div className="space-y-0.5 max-h-64 overflow-y-auto">
            {(Object.keys(languages) as Language[]).map((langKey) => {
              const langInfo = languages[langKey];
              const isSelected = language === langKey;

              return (
                <button
                  key={langKey}
                  onClick={() => handleSelect(langKey)}
                  className={`w-full flex items-center justify-between px-2.5 py-2 rounded-xl text-xs font-bold transition-all text-left cursor-pointer ${
                    isSelected
                      ? "bg-[#CCFF00] text-black shadow-md font-black"
                      : "text-slate-300 hover:text-white hover:bg-slate-800"
                  }`}
                >
                  <div className="flex items-center gap-2">
                    <span className="text-base leading-none">{langInfo.flag}</span>
                    <span>{langInfo.name}</span>
                  </div>
                  {isSelected && <Check className="w-3.5 h-3.5 stroke-[3] text-black" />}
                </button>
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
}
