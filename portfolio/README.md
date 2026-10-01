# Emmanuel Akhigbe — Developer Portfolio

Personal portfolio site built from the [DevPortfolio](https://github.com/RyanFitzgerald/devportfolio) template (Astro + Tailwind CSS v4).

## Editing content

Everything on the page comes from `src/config.ts`: name, title, social links, about text, skills, projects, experience and education. Sections with no data are hidden automatically, and social icons only appear for links that are set.

Lines marked `TODO` in the config still need your input (project descriptions, degree details).

## Local development

```bash
cd portfolio
npm install
npm run dev      # http://localhost:4321
npm run build    # outputs to dist/
npm run preview
```

## Deployment

`.github/workflows/portfolio.yml` builds this folder and publishes it to GitHub Pages whenever `portfolio/**` changes on `main`.
One-time setup: in the repo go to **Settings → Pages** and set **Source** to **GitHub Actions**.

The site will be served at <https://emms434.github.io/Restaurant-Ordering-System/>.

## Credits

Template by Ryan Fitzgerald, MIT licensed (see `LICENSE.md`).
