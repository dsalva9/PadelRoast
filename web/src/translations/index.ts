import { Language, TranslationDictionary, languages } from "./types";
import { en } from "./locales/en";
import { es } from "./locales/es";
import { it } from "./locales/it";
import { fr } from "./locales/fr";
import { sv } from "./locales/sv";
import { nl } from "./locales/nl";
import { pt } from "./locales/pt";
import { de } from "./locales/de";

export const translations: Record<Language, TranslationDictionary> = {
  en,
  es,
  it,
  fr,
  sv,
  nl,
  pt,
  de,
};

export { languages };
export type { Language, TranslationDictionary };
