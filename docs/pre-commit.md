# Validating the repository files with pre-commit

`.pre-commit-config.yaml` holds git hooks that check the files that are
committed to this repository:

| Hook               | Files checked                                          | Tool       |
| ------------------- | ------------------------------------------------------ | ---------- |
| `yaml-syntax`       | `.config.yaml`, `kas/*.yaml`, `.github/**/*.yml`        | `yamllint` |
| `markdown-lint`     | all `*.md` (root, `docs/`, layer READMEs)              | `pymarkdown` |
| `json-syntax`       | `*.json` and `*.json.disabled` in the layers           | `jq`       |
| `toml-syntax`       | `*.toml` in the layers                                 | `python3`  |
| `patch-syntax`      | `*.patch` in the layers                                | `git`      |
| `shell-syntax`      | `*.sh` in the layers                                   | `shellcheck` |
| `pem-certificate`   | `*.crt` in the layers                                  | `openssl`  |
| `pem-key`           | `*.key` in the layers                                  | `openssl`  |

The checks catch the mistakes that break this repository most often: a
kas configuration that no longer parses, a workflow file with a broken
indentation, a container manifest with a stray comma, a carried upstream
patch that got truncated, a shell script with unquoted variables.

Not checked here are the BitBake recipes and appends. They are parsed and
their dependency tree is resolved by the dry-run jobs in CI, see
[ci-build-matrix.md](ci-build-matrix.md).

## Installing the tools

The dev-container already installs `shellcheck`, `jq` and `openssl`, see
`.devcontainer/Dockerfile`. On another host, install them from your
distribution:

```sh
apt-get install shellcheck jq openssl
```

`pre-commit` itself and the two Python linters come from pip. `pre-commit`
installs the linters into a virtual environment on first use, so they do not
have to be installed separately:

```sh
pip3 install pre-commit yamllint pymarkdownlnt
```

## Enabling the hooks

```sh
pip3 install pre-commit
pre-commit install
```

`pre-commit install` writes a `.git/hooks/pre-commit` script into your
checkout, so every `git commit` runs the checks on the files you stage.

## Checking the whole tree

```sh
pre-commit run --all-files
```

This is also what a CI job would run, if you want to add such a job.

## Tuning the rules

Style rules that only affect prose formatting, or that clash with the
Eclipse boilerplate files, are switched off in `.pre-commit-config.yaml`:

- `line-length` and `document-start` for YAML, because the Eclipse license
  header wraps long lines and is not a YAML document start.
- `MD013` (line length), `MD030` (trailing spaces in code blocks), `MD034`
  (list marker style), `MD041` (first level heading), `MD025` (heading
  spacing) for Markdown.

Only structural problems fail a check: broken YAML, malformed JSON or TOML,
a truncated patch, invalid shell syntax. Add or re-enable rules in
`.pre-commit-config.yaml` if you prefer a stricter documentation style.

## Excluded on purpose

- `*.json.template` and `meta-leda-components/recipes-sdv/eclipse-kanto/files/container-management/config.json`
  contain `@PLACEHOLDER@` values that are substituted at install time, so they
  are not valid JSON in the repository.
- `.devcontainer/devcontainer.json` and `.vscode/settings.json` use JSON with
  comments, which `jq` does not accept.
