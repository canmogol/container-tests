# Multi-stage build for Rust Axum application
FROM rust:1.90.0 AS builder

# Create app directory
WORKDIR /app

# Copy manifests
COPY Cargo.toml Cargo.lock* ./

# Create a dummy main.rs to build dependencies
RUN mkdir src && echo "fn main() {}" > src/main.rs

# Build dependencies - this is the caching Docker layer
RUN cargo build --release && rm src/main.rs

# Copy source code
COPY src ./src

# Build the actual application
RUN cargo build --release

# Runtime stage
FROM debian:bookworm-slim

# Install runtime dependencies and create app user
RUN apt-get update && apt-get install -y \
    && rm -rf /var/lib/apt/lists/* \
    && useradd -m -u 1001 appuser

# Copy the binary from builder stage
COPY --from=builder /app/target/release/rust-axum /usr/local/bin/rust-axum

# Change ownership of the binary
RUN chown appuser:appuser /usr/local/bin/rust-axum

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8080

# Set the startup command
CMD ["rust-axum"]