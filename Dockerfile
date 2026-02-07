# Dockerfile for FRC Team 4973 Reefscape Python Development
# Provides a consistent development environment for all team members

FROM python:3.12-slim

# Install system dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    git \
    && rm -rf /var/lib/apt/lists/*

# Create non-root user for safety
RUN useradd -m -s /bin/bash robotpy
USER robotpy
WORKDIR /home/robotpy/robot

# Add local bin to PATH for pip-installed tools
ENV PATH="/home/robotpy/.local/bin:${PATH}"

# Install RobotPy and dependencies
RUN pip install --user --no-cache-dir \
    robotpy[commands2,sim] \
    phoenix6

# Default command shows help
CMD ["python3", "-m", "robotpy", "--help"]
