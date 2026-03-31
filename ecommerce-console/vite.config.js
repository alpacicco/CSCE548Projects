import { defineConfig } from 'vite';

export default defineConfig({
  root: 'web-client',   // 🔥 THIS IS THE KEY
  base: '/CSCE548Projects/',

  build: {
    outDir: '../dist',  // output goes back to root dist
    emptyOutDir: true
  }
});
