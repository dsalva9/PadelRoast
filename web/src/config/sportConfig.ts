export interface RoastSample {
  headline: string;
  body: string;
  tone: "casual" | "balanced" | "savage" | "savage!!";
  tag: string;
  score: string;
  teams: {
    teamA: string;
    teamB: string;
  };
  mvp: string;
  whatsappMessage: string;
}

export interface SportConfig {
  slug: string;
  name: string;
  brandName: string;
  tagline: string;
  shortDesc: string;
  domain: string;
  accentColor: string;      // e.g. '#CCFF00'
  accentCyan: string;       // e.g. '#00E5FF'
  accentCoral: string;      // e.g. '#FF3366'
  iconEmoji: string;
  sportCourtTerm: string;   // e.g. 'Court', 'Pitch', 'Green'
  equipmentTerm: string;    // e.g. 'Racket', 'Boots', 'Club'
  sequels: {
    name: string;
    domain: string;
    icon: string;
    status: "live" | "coming_soon";
  }[];
  phoneScenarios: {
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
  humorTones: {
    id: string;
    name: string;
    badge: string;
    description: string;
    sampleQuote: string;
  }[];
  roastSamples: RoastSample[];
  faqs: {
    question: string;
    answer: string;
  }[];
}

export const activeSport: SportConfig = {
  slug: "padel",
  name: "Padel",
  brandName: "PadelRoast",
  tagline: "Turn every padel match into content worth sharing.",
  shortDesc: "Log the score in 10 seconds. Generate brutal AI roast cards. Drop them straight into your WhatsApp group chat.",
  domain: "padelroast.com",
  accentColor: "#CCFF00",  // Neon Padel Lime
  accentCyan: "#00E5FF",   // Electric Court Blue
  accentCoral: "#FF3366",  // Savage Fire Coral
  iconEmoji: "🎾",
  sportCourtTerm: "Court",
  equipmentTerm: "Racket",
  sequels: [
    { name: "PadelRoast", domain: "padelroast.com", icon: "🎾", status: "live" },
    { name: "TennisRoast", domain: "tennisroast.com", icon: "🎾", status: "coming_soon" },
    { name: "SoccerRoast", domain: "soccerroast.com", icon: "⚽", status: "coming_soon" },
    { name: "PickleballRoast", domain: "pickleballroast.com", icon: "🏓", status: "coming_soon" },
    { name: "GolfingRoast", domain: "golfingroast.com", icon: "⛳", status: "coming_soon" },
  ],
  phoneScenarios: [
    {
      id: "bagel",
      label: "The Bagel 🥯",
      score: "6-1, 6-0",
      matchType: "Friday League Massacre",
      teamWinners: "Carlos & Marcos",
      teamLosers: "David & Alex",
      headline: "A Formal Police Report Filed on Court 3",
      body: "Carlos and Marcos didn't just win; they dismantled two grown adults' dignity. David spent €340 on a carbon fiber racket just to hit the side glass 16 times, while Alex was seen googling tennis lessons in the parking lot before the handshake.",
      whatsappPreview: "🚨 Viernes de Pádel: Carlos & Marcos 6-1 6-0 David & Alex. Rest in peace boys, therapy sessions are on you 😂💀",
    },
    {
      id: "comeback",
      label: "The Choke 🥶",
      score: "0-6, 7-5, 7-6",
      matchType: "Club Championship Quarter",
      teamWinners: "Lucas & Dani",
      teamLosers: "Javi & Pablo",
      headline: "The Greatest Heartbreak in Amateur History",
      body: "Javi and Pablo were up 5-1 in the second set and started celebrating like they just qualified for Premier Padel. Forty minutes later, Lucas and Dani pulled off an impossible robbery while Pablo began visibly trembling on match point.",
      whatsappPreview: "Lucas & Dani just pulled off the heist of the century! From 0-6 1-5 down to victory 🏆. Pablo, do you need a hug? 😭",
    },
    {
      id: "golden-point",
      label: "Golden Point Drama ⚡",
      score: "7-6, 4-6, 7-6",
      matchType: "Sweat-Fest Sunday",
      teamWinners: "Nico & Marc",
      teamLosers: "Sergio & Victor",
      headline: "Two Hours of Sweat for One Mis-Hit Lob",
      body: "After 120 minutes of diving into fences, arguing over line calls, and three medical timeouts for calf cramps, the entire match was decided by Sergio shanking an overhead into the car park roof. Outstanding athleticism, zero hand-eye coordination.",
      whatsappPreview: "What a war! 7-6 in the third. Sergio's smash is currently orbiting Jupiter 🚀🤣",
    },
  ],
  humorTones: [
    {
      id: "casual",
      name: "Casual Banter",
      badge: "🍻 Beer & Tapas",
      description: "Friendly post-match teasing. Good vibes, no friendships ruined.",
      sampleQuote: "“Decent effort out there. The score didn't reflect how nice your matching outfits looked.”",
    },
    {
      id: "balanced",
      name: "Sports Desk",
      badge: "🎙️ ESPN Analyst",
      description: "Analytical humor with clever digs, tactical roast breakdowns, and player stat callouts.",
      sampleQuote: "“A tactical disasterclass on court 2. High unforced error count, questionable shot selection, peak Sunday hobbyist energy.”",
    },
    {
      id: "savage",
      name: "Savage",
      badge: "🔥 Maximum Burn",
      description: "Biting comedy that hits right in the ego. Demolishes expensive equipment and fake injuries.",
      sampleQuote: "“Carlos didn't play padel today; he participated in target practice where the side fence was his main sponsor.”",
    },
    {
      id: "unhinged",
      name: "Savage!!",
      badge: "💀 Unhinged Rant",
      description: "Full dramatic meltdown comedy. Hyperbolic, theatrical, completely relentless.",
      sampleQuote: "“CALL THE FEDERATION. We witnessed an absolute tragedy. Sell your gear, delete Playtomic, and take up birdwatching immediately.”",
    },
  ],
  roastSamples: [
    {
      headline: "The Glass Magnet",
      body: "Marcos spent more time apologizing to the club owner for head-butting the glass than actually returning lobs.",
      tone: "savage",
      tag: "Wall Hugger",
      score: "6-2, 6-1",
      teams: { teamA: "David & Leo", teamB: "Marcos & Nacho" },
      mvp: "The back glass panel (absorbed 42 unforced errors)",
      whatsappMessage: "David & Leo took the W! Marcos, hope the glass has good insurance 🧱💥",
    },
    {
      headline: "The €400 Gear Illusion",
      body: "Pro shoes, neon wristbands, carbon racket with custom grip tape... and an unforced error percentage hovering near 94%.",
      tone: "balanced",
      tag: "All Kit No Hit",
      score: "6-3, 6-4",
      teams: { teamA: "Rafa & Pol", teamB: "Alvaro & Guille" },
      mvp: "Guille's pristine matching headband",
      whatsappMessage: "Looked like Bullpadel sponsored pros, played like bowling pins 🎳",
    },
    {
      headline: "The 'Sun Was In My Eyes' Excuse",
      body: "An indoor court with LED lighting, yet Alex found a way to blame solar flares for missing 8 consecutive bandejas.",
      tone: "savage!!",
      tag: "Astronomer",
      score: "6-0, 6-2",
      teams: { teamA: "Joan & Pau", teamB: "Alex & Miquel" },
      mvp: "The indoor ceiling lamp",
      whatsappMessage: "Indoor solar interference claims will be investigated by NASA 🌞👀",
    },
  ],
  faqs: [
    {
      question: "Do all 4 players need to download the app?",
      answer: "No! That's the superpower of Ghost Profiles. Only ONE person needs PadelRoast. You simply type your friends' names, enter the match score, and generate shareable roast cards into your WhatsApp group in under 15 seconds.",
    },
    {
      question: "Is PadelRoast free to use?",
      answer: "Yes! You can generate roast cards right away for free. We also offer optional Pro tiers for unhinged Savage!! generation and unlimited monthly banter.",
    },
    {
      question: "Can I adjust how mean the jokes are?",
      answer: "Absolutely. Choose from 4 tone levels: Casual (friendly drinks), Balanced (clever commentary), Savage (hilarious burns), and Savage!! (unhinged comedy). You are always in control of the vibe.",
    },
    {
      question: "When is the iOS version coming out?",
      answer: "The Android version is launching first (direct APK beta + Google Play). The iOS app is currently in active preparation and will land on TestFlight and the Apple App Store shortly after.",
    },
    {
      question: "How do I share the roast cards with my friends?",
      answer: "With a single tap! You can share the high-res Roast Card image directly to WhatsApp, Instagram Stories, or copy the pre-written banter message with one click.",
    },
  ],
};
