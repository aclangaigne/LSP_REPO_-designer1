#!/usr/bin/env bash
set -euo pipefail
javac -d out src/org/howard/edu/lsp/assignment2/ETLPipeline.java
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline
