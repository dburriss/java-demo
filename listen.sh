#!/bin/zsh

eval "$(pkgx --shellcode)"
# ^^ integrates `pkgx` during this script execution

env +go
# ^^ requires integration

# Executables are installed in the directory named by the GOBIN environment
# variable, which defaults to $GOPATH/bin or $HOME/go/bin if the GOPATH
# environment variable is not set. Executables in $GOROOT
# are installed in $GOROOT/bin or $GOTOOLDIR instead of $GOBIN.

echo "GOPATH: $GOPATH"
echo "GOROOT: $GOROOT"
echo "GOBIN: $GOBIN"
echo "GOTOOLDIR: $GOTOOLDIR"
echo "HOME GO: $HOME/go/bin"

go install github.com/zendesk/statsd-logger/cmd/statsd-logger@latest

# Concatenate $GOPATH and binary name
binary_path="$HOME/go/bin/statsd-logger"

echo "List"
ls "$HOME/go/bin"

read -k1
# Run the binary
echo "Running binary at $binary_path"
"$binary_path"