# Validating the repository files with pre-commit

`.pre-commit-config.yaml` holds git hooks that check the files that are
committed to this repository:

| Hook             | Files checked                                             | Tool         |
| --------------- | --------------------------------------------------------- | ------------ |
| `yaml-syntax`   | `.config.yaml`, `kas/*.yaml`, `.github/workflows/*.yml`    | `yamllint`   |
| `markdown-lint` | `*.md` in the repository root, `docs/**/*.md`              | `pymarkdown` |

The YAML check keeps the kas build configurations and the GitHub workflow files
syntactically valid, which is what breaks most often when they are edited. The
Markdown check keeps the documentation readable.

## Enabling the hooks

```sh
pip3 install pre-commit
pre-commit install
```

`pre-commit install` writes a `.git/hooks/pre-commit` script into your checkout,
so every `git commit` runs the checks on the files you stage. The linters
themselves are installed into a virtual environment by pre-commit on first use.

## Checking the whole tree

```sh
pre-commit run --all-files
```

This is also what a CI job would run, if you want to add such a job.

## Tuning the rules

Style rules that only affect prose formatting, or that clash with the Eclipse
boilerplate files, are switched off in `.pre-commit-config.yaml`:

- `line-length` and `document-start` for YAML, because the Eclipse license
  header wraps long lines and is not a YAML document start.
- `MD013` (line length), `MD030` (trailing spaces in code blocks), `MD034`
  (list marker style), `MD041` (first level heading), `MD025` (heading
  spacing) for Markdown.

Only structural problems - broken YAML, malformed lists, headings without blank
lines - fail the check. Add or re-enable rules in `.pre-commit-config.yaml` if
you prefer a stricter documentation style.
