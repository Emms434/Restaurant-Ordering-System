// @ts-check
import { defineConfig } from "astro/config";
import tailwindcss from "@tailwindcss/vite";

// https://astro.build/config
export default defineConfig({
  // PORTFOLIO_BASE is set by the Pages workflow (e.g. "/Restaurant-Ordering-System/").
  site: "https://emms434.github.io",
  base: process.env.PORTFOLIO_BASE ?? "/",
  vite: {
    plugins: [tailwindcss()],
  },
});
