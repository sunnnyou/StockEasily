/* Tailwind cheatsheet: https://tailwindcomponents.com/cheatsheet/ */

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,svelte,ts}'],
  theme: {
    extend: {
      width: {
        '34p': '34%',
        '65p': '65%',
      }
    },
  },
  plugins: [],
}
