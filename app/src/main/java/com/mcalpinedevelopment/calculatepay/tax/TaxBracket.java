package com.mcalpinedevelopment.calculatepay.tax;

/**
* Created by kurt on 12/11/14.
*/
class TaxBracket implements Comparable<TaxBracket> {
    private double mLower;
    private double mUpper;
    private double mPercentage;

    public TaxBracket(double lower, double upper, double percentage) {
        if (lower < upper && lower >= 0 && percentage >= 0) {
            mLower = lower;
            mUpper = upper;
            mPercentage = percentage;
        } else {
            throw new IllegalArgumentException("lower must be less than upper as well as larger than or equal to zero");
        }
    }

    public double getLower() {
        return mLower;
    }

    public double getUpper() {
        return mUpper;
    }

    public double getRange() {
        return mUpper - mLower;
    }

    public double getPercentage() {
        return mPercentage;
    }

    @Override
    public int compareTo(TaxBracket another) {
        if (mLower < another.mLower) {
            return -1;
        } else if (mLower > another.mLower) {
            return 1;
        } else {
            if (mUpper < another.mUpper) {
                return -1;
            } else if (mUpper > another.mUpper) {
                return 1;
            }
        }
        return 0;
    }

    public boolean intersects(TaxBracket another) {
        if (mLower < another.mUpper && another.mLower < mLower)
            return true;

        if (another.mLower < mUpper && mLower < another.mLower)
            return true;

        return false;
    }
}
