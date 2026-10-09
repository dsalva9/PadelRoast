export type Language = "en" | "es" | "it" | "fr" | "sv" | "nl" | "pt" | "de";

export interface TranslationDictionary {
  localeName: string;
  flag: string;
  nav: {
    demo: string;
    tones: string;
    howItWorks: string;
    roastDeck: string;
    faq: string;
    iosComingSoon: string;
    downloadApk: string;
  };
  hero: {
    badgeEngine: string;
    badgeBeta: string;
    stickers: {
      bagelVictim: string;
      expensiveRacket: string;
      chatDestroyer: string;
    };
    headlineMain: string;
    headlineHighlight: string;
    subhead: string;
    pill1: string;
    pill2: string;
    pill3: string;
    ctaApkTitle: string;
    ctaApkSub: string;
    ctaPlayTitle: string;
    ctaPlaySub: string;
    iosNotice: string;
    proofFree: string;
    proofGhost: string;
    proofWhatsApp: string;
    teaserBadge: string;
    teaserTitle: string;
  };
  phone: {
    scenarios: {
      id: string;
      label: string;
      score: string;
      matchType: string;
      teamWinners: string;
      teamLosers: string;
      headline: string;
      body: string;
      whatsappPreview: string;
    }[];
    steps: {
      matchLogged: string;
      court: string;
      finished: string;
      analyzing: string;
      selectIntensity: string;
      savageBadge: string;
      savageDesc: string;
      savageRoastTag: string;
      matchMvp: string;
      mvpQuote: string;
      shareBtn: string;
      simulatedShare: string;
      chatTitle: string;
      chatMembers: string;
      reportCardTag: string;
      chatErupts: string;
      stepCount: string;
      nextStep: string;
      restartDemo: string;
    };
  };
  tonesSection: {
    badge: string;
    titleMain: string;
    titleHighlight: string;
    subtitle: string;
    activeToneMode: string;
    intensityLabel: string;
    aiOutputLabel: string;
    engineLabel: string;
    prefilledLabel: string;
    readyLabel: string;
    tones: {
      id: string;
      level: string;
      name: string;
      badge: string;
      vibe: string;
      quote: string;
      whatsapp: string;
    }[];
  };
  ghostSection: {
    badge: string;
    titleMain: string;
    titleHighlight: string;
    subtitle: string;
    otherAppsBadge: string;
    otherAppsTitle: string;
    otherAppsBullets: string[];
    otherAppsResult: string;
    padelRoastBadge: string;
    padelRoastTag: string;
    padelRoastTitle: string;
    padelRoastBullets: {
      strong: string;
      text: string;
    }[];
    padelRoastResult: string;
  };
  cardsSection: {
    badge: string;
    titleMain: string;
    titleHighlight: string;
    subtitle: string;
    cards: {
      title: string;
      subtitle: string;
      badge: string;
      badgeColor: string;
      image: string;
      score: string;
      quote: string;
      whatsapp: string;
      awardLabel: string;
      awardText: string;
    }[];
    copyBtn: string;
    copiedBtn: string;
  };
  whatsappSection: {
    badge: string;
    title: string;
    subtitle: string;
    chatTitle: string;
    chatMembers: string;
    online: string;
    friendMessageName: string;
    friendMessageText: string;
    reportHeader: string;
    cardTitle: string;
    cardDesc: string;
    officialMsg: string;
    alexName: string;
    alexMsg: string;
  };
  downloadSection: {
    badge: string;
    title: string;
    subtitle: string;
    btnApkTitle: string;
    btnApkSub: string;
    btnStoreTitle: string;
    btnStoreSub: string;
    downloadSuccess: string;
    tip1: { title: string; desc: string };
    tip2: { title: string; desc: string };
    tip3: { title: string; desc: string };
  };
  faqSection: {
    badge: string;
    title: string;
    faqs: { question: string; answer: string }[];
  };
  footerSection: {
    tagline: string;
    networkTitle: string;
    copyright: string;
    disclaimer: string;
  };
}

export const languages: Record<Language, { name: string; flag: string }> = {
  en: { name: "English", flag: "🇬🇧" },
  es: { name: "Español", flag: "🇪🇸" },
  it: { name: "Italiano", flag: "🇮🇹" },
  fr: { name: "Français", flag: "🇫🇷" },
  sv: { name: "Svenska", flag: "🇸🇪" },
  nl: { name: "Nederlands", flag: "🇳🇱" },
  pt: { name: "Português", flag: "🇵🇹" },
  de: { name: "Deutsch", flag: "🇩🇪" },
};
