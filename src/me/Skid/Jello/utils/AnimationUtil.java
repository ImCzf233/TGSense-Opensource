package me.Skid.Jello.utils;

import java.awt.Color;

public class AnimationUtil {

    public static float fastmax(float a, float b) {
        return a >= b ? a : b;
    }

    public static float fastmin(float a, float b) {
        return a <= b ? a : b;
    }

    public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = (end - current) * smoothSpeed;

        if (movement > 0.0F) {
            movement = fastmax(minSpeed, movement);
            movement = fastmin(end - current, movement);
        } else if (movement < 0.0F) {
            movement = fastmin(-minSpeed, movement);
            movement = fastmax(end - current, movement);
        }

        return current + movement;
    }

    public static int moveUDl(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = (end - current) * smoothSpeed;

        if (movement > 0.0F) {
            movement = Math.max(minSpeed, movement);
            movement = Math.min(end - current, movement);
        } else if (movement < 0.0F) {
            movement = Math.min(-minSpeed, movement);
            movement = Math.max(end - current, movement);
        }

        return (int) (current + movement);
    }

    public static float calculateCompensation(float target, float current, float f, float g) {
        float diff = current - target;

        if (f < 1.0F) {
            f = 1.0F;
        }

        double xD;

        if (diff > g) {
            xD = (double) (g * f / 16.0F) < 0.25D ? 0.5D : (double) (g * f / 16.0F);
            current -= (float) xD;
            if (current < target) {
                current = target;
            }
        } else if (diff < -g) {
            xD = (double) (g * f / 16.0F) < 0.25D ? 0.5D : (double) (g * f / 16.0F);
            current += (float) xD;
            if (current > target) {
                current = target;
            }
        } else {
            current = target;
        }

        return current;
    }

    public static float calculateCompensation(float target, float current, long delta, int speed) {
        float diff = current - target;

        if (delta < 1L) {
            delta = 1L;
        }

        double xD;

        if (diff > (float) speed) {
            xD = (double) ((long) speed * delta / 16L) < 0.25D ? 0.5D : (double) ((long) speed * delta / 16L);
            current -= (float) xD;
            if (current < target) {
                current = target;
            }
        } else if (diff < (float) (-speed)) {
            xD = (double) ((long) speed * delta / 16L) < 0.25D ? 0.5D : (double) ((long) speed * delta / 16L);
            current += (float) xD;
            if (current > target) {
                current = target;
            }
        } else {
            current = target;
        }

        return current;
    }

    public static double getAnimationState(double animation, double finalState, double speed) {
        float add = (float) (0.01D * speed);

        animation = animation < finalState ? Math.min(animation + (double) add, finalState) : Math.max(animation - (double) add, finalState);
        return animation;
    }

    public static float getAnimationState(float animation, float finalState, float speed) {
        float add = (float) (0.01D * (double) speed);

        animation = animation < finalState ? Math.min(animation + add, finalState) : Math.max(animation - add, finalState);
        return animation;
    }

    public static int animatel(float target, float current, float speed) {
        if (TimerUtils.delay(4.0F)) {
            boolean larger = target > current;

            if (speed < 0.0F) {
                speed = 0.0F;
            } else if ((double) speed > 1.0D) {
                speed = 1.0F;
            }

            float dif = Math.max(target, current) - Math.min(target, current);
            float factor = dif * speed;

            if (factor < 0.1F) {
                factor = 0.1F;
            }

            current = larger ? current + factor : current - factor;
            TimerUtils.reset();
        }

        return (double) Math.abs(current - target) < 0.2D ? (int) target : (int) current;
    }

    public static float animate(float target, float current, float speed) {
        if (TimerUtils.delay(4.0F)) {
            boolean larger = target > current;

            if (speed < 0.0F) {
                speed = 0.0F;
            } else if ((double) speed > 1.0D) {
                speed = 1.0F;
            }

            float dif = Math.max(target, current) - Math.min(target, current);
            float factor = dif * speed;

            if (factor < 0.1F) {
                factor = 0.1F;
            }

            current = larger ? current + factor : current - factor;
            TimerUtils.reset();
        }

        return (double) Math.abs(current - target) < 0.2D ? target : current;
    }

    public static double animate(double target, double current, double speed) {
        boolean larger = target > current;

        if (speed < 0.0D) {
            speed = 0.0D;
        } else if (speed > 1.0D) {
            speed = 1.0D;
        }

        if (target == current) {
            return target;
        } else {
            double dif = Math.max(target, current) - Math.min(target, current);
            double factor = Math.max(dif * speed, 1.0D);

            if (factor < 0.1D) {
                factor = 0.1D;
            }

            if (larger) {
                if (current + factor > target) {
                    current = target;
                } else {
                    current += factor;
                }
            } else if (current - factor < target) {
                current = target;
            } else {
                current -= factor;
            }

            return current;
        }
    }

    public static Color getColorAnimationState(Color animation, Color finalState, double speed) {
        float add = (float) (0.01D * speed);
        float animationr = (float) animation.getRed();
        float animationg = (float) animation.getGreen();
        float animationb = (float) animation.getBlue();
        float finalStater = (float) finalState.getRed();
        float finalStateg = (float) finalState.getGreen();
        float finalStateb = (float) finalState.getBlue();
        float finalStatea = (float) finalState.getAlpha();

        if (animationr < finalStater) {
            if (animationr + add < finalStater) {
                animationr += add;
            } else {
                animationr = finalStater;
            }
        } else if (animationr - add > finalStater) {
            animationr -= add;
        } else {
            animationr = finalStater;
        }

        if (animationg < finalStateg) {
            if (animationg + add < finalStateg) {
                animationg += add;
            } else {
                animationg = finalStateg;
            }
        } else if (animationg - add > finalStateg) {
            animationg -= add;
        } else {
            animationg = finalStateg;
        }

        if (animationb < finalStateb) {
            if (animationb + add < finalStateb) {
                animationb += add;
            } else {
                animationb = finalStateb;
            }
        } else if (animationb - add > finalStateb) {
            animationb -= add;
        } else {
            animationb = finalStateb;
        }

        animationr /= 255.0F;
        animationg /= 255.0F;
        animationb /= 255.0F;
        finalStatea /= 255.0F;
        if (animationr > 1.0F) {
            animationr = 1.0F;
        }

        if (animationg > 1.0F) {
            animationg = 1.0F;
        }

        if (animationb > 1.0F) {
            animationb = 1.0F;
        }

        if (finalStatea > 1.0F) {
            finalStatea = 1.0F;
        }

        return new Color(animationr, animationg, animationb, finalStatea);
    }

    public static float clamp(float number, float min, float max) {
        return number < min ? min : Math.min(number, max);
    }
}
