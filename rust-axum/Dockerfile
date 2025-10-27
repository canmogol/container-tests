# Multi-stage build for Rust Axum application
FROM rust:1.90.0 AS builder

# Create app directory
WORKDIR /app

# Copy manifests
COPY Cargo.toml Cargo.lock* ./

# Copy source code
COPY src ./src

# Build the actual application
RUN cargo build --release

# Runtime stage
FROM debian:bookworm-slim

# Install runtime dependencies and create app user
RUN apt-get update && apt-get install -y libssl-dev build-essential pkg-config \
    && rm -rf /var/lib/apt/lists/*

# Copy the binary from builder stage
COPY --from=builder /app/target/release/rust-axum /usr/local/bin/rust-axum

# Expose port
EXPOSE 8080

# Set the startup command
CMD ["rust-axum"]
