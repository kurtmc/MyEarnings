package com.mcalpinedevelopment.calculatepay.tax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
* Created by kurt on 12/11/14.
*/
class TaxBrackets implements Iterable<TaxBracket>, Iterator<TaxBracket> {
    List<TaxBracket> mTaxBracketList;

    public void addBracket(TaxBracket taxBracket) {
        if (mTaxBracketList == null) {
            mTaxBracketList = new ArrayList<TaxBracket>();
        }
        for (TaxBracket r : mTaxBracketList) {
            if (r.intersects(taxBracket)) {
                throw new IllegalArgumentException("Ranges cannot intersect");
            }
        }

        mTaxBracketList.add(taxBracket);
        Collections.sort(mTaxBracketList);
    }

    private int count = 0;

    @Override
    public Iterator<TaxBracket> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return count < mTaxBracketList.size();
    }

    @Override
    public TaxBracket next() {
        return mTaxBracketList.get(count++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
