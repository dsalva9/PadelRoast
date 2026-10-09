"use client";

import React, { createContext, useContext, useState, useEffect } from "react";
import { Language, TranslationDictionary, translations, languages } from "@/translations";

interface LanguageContextType {
  language: Language;
  setLanguage: (lang: Language) => void;
  t: TranslationDictionary;
}

const LanguageContext = createContext<LanguageContextType | undefined>(undefined);

const STORAGE_KEY = "padelroast_lang";

const detectBrowserLanguage = (): Language => {
  if (typeof window === "undefined" || !navigator.language) {
    return "en";
  }

  const browserLang = navigator.language.toLowerCase();
  
  if (browserLang.startsWith("es")) return "es";
  if (browserLang.startsWith("it")) return "it";
  if (browserLang.startsWith("fr")) return "fr";
  if (browserLang.startsWith("sv") || browserLang.startsWith("se")) return "sv";
  if (browserLang.startsWith("nl")) return "nl";
  if (browserLang.startsWith("pt")) return "pt";
  if (browserLang.startsWith("de")) return "de";

  return "en";
};

export const LanguageProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [language, setLanguageState] = useState<Language>("en");
  const [isInitialized, setIsInitialized] = useState(false);

  useEffect(() => {
    try {
      const saved = localStorage.getItem(STORAGE_KEY) as Language | null;
      if (saved && saved in translations) {
        setLanguageState(saved);
        document.documentElement.lang = saved;
      } else {
        const detected = detectBrowserLanguage();
        setLanguageState(detected);
        document.documentElement.lang = detected;
      }
    } catch {
      // fallback
    }
    setIsInitialized(true);
  }, []);

  const setLanguage = (lang: Language) => {
    setLanguageState(lang);
    try {
      localStorage.setItem(STORAGE_KEY, lang);
      document.documentElement.lang = lang;
    } catch {
      // ignore storage errors
    }
  };

  const t = translations[language];

  return (
    <LanguageContext.Provider value={{ language, setLanguage, t }}>
      {children}
    </LanguageContext.Provider>
  );
};

export const useLanguage = (): LanguageContextType => {
  const context = useContext(LanguageContext);
  if (!context) {
    throw new Error("useLanguage must be used within a LanguageProvider");
  }
  return context;
};
