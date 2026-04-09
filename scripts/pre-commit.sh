#!/usr/bin/env bash
set -euo pipefail

echo "[pre-commit] Running Spotless auto-format..."
mvn -q -ntp spotless:apply
git add -A

echo "[pre-commit] Running Checkstype..."
mvn -q -ntp checkstyle:check

if command -v javaWhitelist >/dev/null 2>&1; then
  echo "[pre-commit] Running javaWhitelist on all modules..."

  found_any=0
  while IFS= read -r d; do
    found_any=1
    echo "[pre-commit] javaWhitelist $d"
    javaWhitelist "$d"
  done < <(find . -type d -path '*/src/main/java' -print | sort)

  if [ "$found_any" -eq 0 ]; then
    echo "[pre-commit] No src/main/java directories found, skipping."
  fi
else
  echo "[pre-commit] javaWhitelist not found on PATH, skipping."
fi
