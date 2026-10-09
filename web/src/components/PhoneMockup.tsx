"use client";

import React, { useState, useEffect } from "react";
import { activeSport } from "@/config/sportConfig";
import { Flame, Share2, Sparkles, Trophy, ChevronRight, Play, Pause, Smartphone } from "lucide-react";

export default function PhoneMockup() {
  const [selectedScenarioIndex, setSelectedScenarioIndex] = useState(0);
  const [activeStep, setActiveStep] = useState(0); // 0: Match Result, 1: Tone Picking, 2: Roast Card, 3: WhatsApp Share
  const [isPlaying, setIsPlaying] = useState(true);

  const scenario = activeSport.phoneScenarios[selectedScenarioIndex];

  // Auto-play stepper loop
  useEffect(() => {
    if (!isPlaying) return;

    const timer = setInterval(() => {
      setActiveStep((prev) => {
        if (prev < 3) return prev + 1;
        // loop to next scenario after step 3
        setSelectedScenarioIndex((s) => (s + 1) % activeSport.phoneScenarios.length);
        return 0;
      });
    }, 3800);

    return () => clearInterval(timer);
  }, [isPlaying]);

  return (
    <div className="relative flex flex-col items-center">
      {/* Ambient Radial Glow behind phone */}
      <div 
        className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[340px] sm:w-[420px] h-[640px] rounded-[3.5rem] pointer-events-none blur-3xl opacity-40 transition-all duration-700"
        style={{
          background: "radial-gradient(circle, rgba(204,255,0,0.35) 0%, rgba(0,229,255,0.2) 40%, rgba(11,15,25,0) 70%)"
        }}
      />

      {/* Scenario Selector Pills */}
      <div className="flex items-center gap-1.5 p-1 mb-4 bg-slate-900/90 border border-slate-800 rounded-full z-20 backdrop-blur-md max-w-full overflow-x-auto shadow-lg">
        {activeSport.phoneScenarios.map((sc, idx) => (
          <button
            key={sc.id}
            onClick={() => {
              setSelectedScenarioIndex(idx);
              setActiveStep(2); // Jump straight to roast card on manual tap
            }}
            className={`text-xs font-semibold px-3 py-1.5 rounded-full transition-all duration-200 whitespace-nowrap ${
              selectedScenarioIndex === idx
                ? "bg-[#CCFF00] text-black shadow-md font-bold scale-[1.02]"
                : "text-slate-400 hover:text-white hover:bg-slate-800"
            }`}
          >
            {sc.label}
          </button>
        ))}

        <button
          onClick={() => setIsPlaying(!isPlaying)}
          title={isPlaying ? "Pause auto-demo" : "Play auto-demo"}
          className="px-2 py-1.5 text-slate-400 hover:text-[#CCFF00] transition-colors"
        >
          {isPlaying ? <Pause className="w-3.5 h-3.5" /> : <Play className="w-3.5 h-3.5" />}
        </button>
      </div>

      {/* The Physical Phone Bezel */}
      <div className="relative w-[300px] sm:w-[320px] h-[620px] bg-slate-950 border-[10px] border-slate-900 rounded-[3rem] shadow-[0_25px_60px_-15px_rgba(0,0,0,0.9),0_0_40px_rgba(204,255,0,0.12)] flex flex-col overflow-hidden ring-1 ring-white/10 z-10 transition-all select-none">
        
        {/* Dynamic Island / Notch */}
        <div className="h-7 w-full absolute top-0 left-0 z-30 flex justify-center items-center pointer-events-none">
          <div className="h-4.5 w-24 bg-black rounded-full flex items-center justify-between px-2.5">
            <div className="w-2.5 h-2.5 rounded-full bg-slate-900 border border-slate-800"></div>
            <div className="w-1.5 h-1.5 rounded-full bg-emerald-500/80 animate-pulse"></div>
          </div>
        </div>

        {/* Screen Status Bar */}
        <div className="pt-2 px-5 pb-1 flex justify-between items-center text-[10px] text-slate-400 font-semibold z-20">
          <span>19:42</span>
          <div className="flex items-center gap-1.5">
            <span>5G</span>
            <div className="w-4 h-2 border border-slate-400 rounded-sm p-0.5 flex items-center">
              <div className="h-full w-full bg-[#CCFF00] rounded-xs"></div>
            </div>
          </div>
        </div>

        {/* Phone Content Screen */}
        <div className="flex-1 bg-[#0B0F19] text-white flex flex-col overflow-hidden relative p-3.5 pt-1">
          
          {/* App Topbar */}
          <div className="flex items-center justify-between py-1 mb-2 border-b border-slate-800/80">
            <div className="flex items-center gap-1.5">
              <div className="w-6 h-6 rounded-lg bg-gradient-to-tr from-amber-500 to-[#CCFF00] flex items-center justify-center text-xs shadow-sm">
                🔥
              </div>
              <span className="font-black text-xs tracking-tight text-white">
                PADEL<span className="text-[#CCFF00]">ROAST</span>
              </span>
            </div>
            <span className="text-[9px] uppercase tracking-wider font-bold px-2 py-0.5 rounded-full bg-slate-800 text-[#00E5FF] border border-[#00E5FF]/30">
              AI ENGINE
            </span>
          </div>

          {/* Stepper Progress Indicator */}
          <div className="grid grid-cols-4 gap-1 mb-3">
            {[0, 1, 2, 3].map((step) => (
              <div
                key={step}
                onClick={() => setActiveStep(step)}
                className={`h-1 rounded-full cursor-pointer transition-all duration-300 ${
                  activeStep === step
                    ? "bg-[#CCFF00]"
                    : activeStep > step
                    ? "bg-[#00E5FF]"
                    : "bg-slate-800"
                }`}
              />
            ))}
          </div>

          {/* Inner Animated Step Screen */}
          <div className="flex-1 flex flex-col justify-between overflow-hidden">
            {activeStep === 0 && (
              <div className="flex-1 flex flex-col justify-center animate-in fade-in zoom-in-95 duration-300">
                <div className="text-[10px] uppercase font-bold text-slate-400 mb-1 flex items-center gap-1">
                  <span>Match Logged</span> • <span>Court 3</span>
                </div>
                <div className="bg-slate-900/90 border border-slate-800 rounded-2xl p-3.5 shadow-lg">
                  <div className="flex justify-between items-center mb-3">
                    <span className="text-[11px] font-semibold text-slate-400">{scenario.matchType}</span>
                    <span className="text-[10px] font-bold px-2 py-0.5 rounded bg-rose-500/20 text-rose-400 border border-rose-500/30">
                      Finished
                    </span>
                  </div>

                  {/* Team Winners */}
                  <div className="flex items-center justify-between bg-slate-800/60 p-2.5 rounded-xl mb-2 border border-slate-700/50">
                    <div className="flex items-center gap-2">
                      <div className="w-5 h-5 rounded-full bg-[#CCFF00]/20 text-[#CCFF00] flex items-center justify-center text-[10px] font-black">
                        W
                      </div>
                      <span className="text-xs font-bold text-white">{scenario.teamWinners}</span>
                    </div>
                    <span className="text-xs font-black text-[#CCFF00] font-mono">
                      {scenario.score.split(",")[0].trim()}
                    </span>
                  </div>

                  {/* Team Losers */}
                  <div className="flex items-center justify-between bg-slate-950/60 p-2.5 rounded-xl border border-slate-800">
                    <div className="flex items-center gap-2">
                      <div className="w-5 h-5 rounded-full bg-slate-800 text-slate-400 flex items-center justify-center text-[10px]">
                        L
                      </div>
                      <span className="text-xs font-medium text-slate-300">{scenario.teamLosers}</span>
                    </div>
                    <span className="text-xs font-black text-slate-400 font-mono">
                      {scenario.score.split(",")[1]?.trim() || "0"}
                    </span>
                  </div>
                </div>

                <div className="mt-3 text-center">
                  <span className="text-[11px] text-slate-400 animate-pulse">
                    Analyzing match dynamics... 🎾
                  </span>
                </div>
              </div>
            )}

            {activeStep === 1 && (
              <div className="flex-1 flex flex-col justify-center animate-in fade-in zoom-in-95 duration-300">
                <div className="text-[10px] uppercase font-bold text-slate-400 mb-2">
                  Select Banter Intensity
                </div>

                <div className="space-y-2">
                  <div className="p-2.5 rounded-xl bg-slate-900 border border-slate-800 text-left opacity-60">
                    <div className="flex justify-between items-center text-xs font-bold text-slate-300">
                      <span>Casual Banter</span>
                      <span className="text-[10px] text-slate-400">Light</span>
                    </div>
                  </div>

                  <div className="p-2.5 rounded-xl bg-slate-900 border border-slate-800 text-left opacity-60">
                    <div className="flex justify-between items-center text-xs font-bold text-slate-300">
                      <span>Sports Desk</span>
                      <span className="text-[10px] text-slate-400">Analyst</span>
                    </div>
                  </div>

                  {/* Selected Savage Button */}
                  <div className="p-3 rounded-xl bg-gradient-to-r from-rose-950/80 to-slate-900 border-2 border-rose-500 shadow-[0_0_20px_rgba(244,63,94,0.3)] text-left transform scale-[1.02] transition-all">
                    <div className="flex justify-between items-center text-xs font-extrabold text-rose-400">
                      <span className="flex items-center gap-1.5">
                        <Flame className="w-3.5 h-3.5 fill-rose-500 text-rose-500" />
                        Savage 🔥
                      </span>
                      <span className="text-[9px] bg-rose-500 text-black px-1.5 py-0.5 rounded font-black uppercase">
                        Active
                      </span>
                    </div>
                    <p className="text-[10px] text-slate-300 mt-1">
                      Direct hits on expensive rackets & unforced errors.
                    </p>
                  </div>

                  <div className="p-2.5 rounded-xl bg-slate-900 border border-slate-800 text-left opacity-60">
                    <div className="flex justify-between items-center text-xs font-bold text-slate-300">
                      <span>Savage!! 💀</span>
                      <span className="text-[10px] text-slate-400">Unhinged</span>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeStep === 2 && (
              <div className="flex-1 flex flex-col justify-between animate-in fade-in zoom-in-95 duration-300 py-1">
                {/* The Hero Roast Card */}
                <div className="relative rounded-2xl bg-gradient-to-b from-slate-900 via-slate-900 to-[#131B2E] border-2 border-[#CCFF00]/60 p-3.5 shadow-[0_0_25px_rgba(204,255,0,0.18)] flex-1 flex flex-col justify-between">
                  <div className="flex justify-between items-start">
                    <span className="text-[9px] font-black uppercase tracking-wider text-black bg-[#CCFF00] px-2 py-0.5 rounded-md">
                      SAVAGE ROAST
                    </span>
                    <span className="text-[10px] font-mono text-[#00E5FF] font-bold">
                      {scenario.score}
                    </span>
                  </div>

                  {/* Mini Card Image Header */}
                  <div className="relative h-18 w-full rounded-xl overflow-hidden my-1 bg-slate-950 border border-slate-800">
                    <img
                      src={
                        selectedScenarioIndex === 0
                          ? "/images/padel-player-shock.jpg"
                          : selectedScenarioIndex === 1
                          ? "/images/bagel-trophy.jpg"
                          : "/images/padel-racket-fire.jpg"
                      }
                      alt="Roast Scene"
                      className="object-cover w-full h-full"
                    />
                    <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-transparent to-transparent" />
                    <span className="absolute bottom-1 left-2 text-[9px] font-black text-[#CCFF00]">
                      EVIDENCE #0{selectedScenarioIndex + 1}
                    </span>
                  </div>

                  <div className="my-1">
                    <h4 className="text-xs font-black text-white leading-tight mb-1">
                      {scenario.headline}
                    </h4>
                    <p className="text-[9px] text-slate-300 leading-snug line-clamp-3">
                      {scenario.body}
                    </p>
                  </div>

                  <div className="bg-slate-950/80 p-1.5 rounded-lg border border-slate-800/80">
                    <div className="text-[7px] uppercase font-black text-slate-400">Match MVP</div>
                    <div className="text-[8px] text-[#00E5FF] font-semibold truncate">
                      The side glass panel (absorbed 16 direct shanks)
                    </div>
                  </div>
                </div>

                <div className="mt-2 text-center">
                  <button 
                    onClick={() => setActiveStep(3)}
                    className="w-full py-2 bg-[#CCFF00] hover:bg-[#b8e600] text-black font-black text-xs rounded-xl flex items-center justify-center gap-1.5 transition-transform active:scale-95 shadow-md"
                  >
                    <Share2 className="w-3.5 h-3.5" />
                    Share Roast to WhatsApp
                  </button>
                </div>
              </div>
            )}

            {activeStep === 3 && (
              <div className="flex-1 flex flex-col justify-center animate-in fade-in zoom-in-95 duration-300">
                <div className="text-[10px] uppercase font-bold text-slate-400 mb-2 flex items-center gap-1">
                  <span>Simulated WhatsApp Share</span>
                </div>

                {/* WhatsApp Chat Preview Card */}
                <div className="rounded-2xl bg-[#0b141a] border border-emerald-900/60 p-3 shadow-xl text-left relative overflow-hidden">
                  <div className="flex items-center gap-2 pb-2 mb-2 border-b border-emerald-950">
                    <div className="w-5 h-5 rounded-full bg-emerald-600 text-[10px] flex items-center justify-center font-bold text-white">
                      🎾
                    </div>
                    <div>
                      <div className="text-[10px] font-bold text-slate-200">Viernes de Pádel 🏆</div>
                      <div className="text-[8px] text-slate-400">4 members • Active now</div>
                    </div>
                  </div>

                  <div className="bg-[#1f2c34] p-2.5 rounded-xl border border-emerald-500/20 text-white space-y-1.5 shadow-sm">
                    <div className="text-[10px] text-emerald-400 font-bold flex items-center gap-1">
                      🔥 PadelRoast Report Card
                    </div>
                    <div className="text-[9px] text-slate-200 leading-snug">
                      {scenario.whatsappPreview}
                    </div>
                    <div className="text-[8px] text-slate-400 text-right">19:43 ✓✓</div>
                  </div>

                  {/* Reaction Emojis Float */}
                  <div className="flex items-center gap-1 mt-2 bg-slate-900/90 px-2 py-1 rounded-full w-fit border border-slate-700 text-xs">
                    <span>😂 3</span>
                    <span>💀 2</span>
                    <span>🎾 1</span>
                  </div>
                </div>

                <div className="mt-4 text-center">
                  <span className="text-[10px] text-[#CCFF00] font-bold">
                    ✓ Group chat erupts in 15 seconds
                  </span>
                </div>
              </div>
            )}
          </div>

          {/* Bottom Step Indicator Bar */}
          <div className="pt-2 border-t border-slate-800/80 flex items-center justify-between text-[9px] text-slate-400">
            <span>Step {activeStep + 1} of 4</span>
            <button 
              onClick={() => setActiveStep((prev) => (prev + 1) % 4)}
              className="text-[#CCFF00] font-bold hover:underline flex items-center gap-0.5"
            >
              {activeStep === 3 ? "Restart Demo" : "Next Step"}
              <ChevronRight className="w-3 h-3" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
