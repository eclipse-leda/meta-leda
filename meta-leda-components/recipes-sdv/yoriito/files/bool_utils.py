def strtobool(value: str) -> int:
    value = value.lower()
    if value in ("y", "yes", "t", "true", "on", "1"):
        return 1
    if value in ("n", "no", "f", "false", "off", "0"):
        return 0
    raise ValueError(f"invalid truth value {value!r}")
