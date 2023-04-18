package com.cradlevendor.customui

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter

import java.util.Arrays
import java.util.LinkedList

class MultiSelectionSpinner : androidx.appcompat.widget.AppCompatSpinner, OnMultiChoiceClickListener {
    private var listener: OnMultipleItemsSelectedListener? = null

    internal var _items: Array<String>? = null
    internal var mSelection: BooleanArray? = null
    internal var mSelectionAtStart: BooleanArray? = null
    internal var _itemsAtStart: String? = null

    internal var simple_adapter: ArrayAdapter<String>

    val selectedStrings: List<String>
        get() {
            val selection = LinkedList<String>()
            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    selection.add(_items!![i])
                }
            }
            return selection
        }

    val selectedIndices: List<Int>
        get() {
            val selection = LinkedList<Int>()
            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    selection.add(i)
                }
            }
            return selection
        }

    val selectedItemsAsString: String
        get() {
            val sb = StringBuilder()
            var foundOne = false

            for (i in _items!!.indices) {
                if (mSelection!![i]) {
                    if (foundOne) {
                        sb.append(", ")
                    }
                    foundOne = true
                    sb.append(_items!![i])
                }
            }
            return sb.toString()
        }

    interface OnMultipleItemsSelectedListener {
        fun selectedIndices(indices: List<Int>)
        fun selectedStrings(strings: List<String>)
    }

    constructor(context: Context) : super(context) {

        simple_adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item
        )
        super.setAdapter(simple_adapter)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        simple_adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item
        )
        super.setAdapter(simple_adapter)
    }

    fun setListener(listener: OnMultipleItemsSelectedListener) {
        this.listener = listener
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if (mSelection != null && which < mSelection!!.size) {
            mSelection!![which] = isChecked
            simple_adapter.clear()
            simple_adapter.add(buildSelectedItemString())
        } else {
            throw IllegalArgumentException(
                "Argument 'which' is out of bounds."
            )
        }
    }

    /*   @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.pls_select_lang);
        builder.setMultiChoiceItems(_items, mSelection, this);
        _itemsAtStart = getSelectedItemsAsString();
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
                listener.selectedIndices(getSelectedIndices());
                listener.selectedStrings(getSelectedStrings());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                simple_adapter.clear();
                simple_adapter.add(_itemsAtStart);
                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
            }
        });
        builder.show();
        return true;
    }*/

    override fun setAdapter(adapter: SpinnerAdapter) {
        throw RuntimeException(
            "setAdapter is not supported by MultiSelectSpinner."
        )
    }

    fun setItems(items: Array<String>) {
        _items = items
        mSelection = BooleanArray(_items!!.size)
        mSelectionAtStart = BooleanArray(_items!!.size)
        simple_adapter.clear()
        simple_adapter.add(_items!![0])
        Arrays.fill(mSelection, false)
        mSelection!![0] = true
        mSelectionAtStart!![0] = true
    }

    fun setItems(items: List<String>) {
        _items = items.toTypedArray()
        mSelection = BooleanArray(_items!!.size)
        mSelectionAtStart = BooleanArray(_items!!.size)
        simple_adapter.clear()
        simple_adapter.add(_items!![0])
        Arrays.fill(mSelection, false)
        mSelection!![0] = true
    }

    fun setSelection(selection: Array<String>) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart?.set(i, false)
        }
        for (cell in selection) {
            for (j in _items!!.indices) {
                if (_items!![j] == cell) {
                    mSelection!![j] = true
                    mSelectionAtStart!!.set(j, true)
                }
            }
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    fun setSelection(selection: List<String>) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        for (sel in selection) {
            for (j in _items!!.indices) {
                if (_items!![j] == sel) {
                    mSelection!![j] = true
                    mSelectionAtStart!![j] = true
                }
            }
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    override fun setSelection(index: Int) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        if (index >= 0 && index < mSelection!!.size) {
            mSelection!![index] = true
            mSelectionAtStart!![index] = true
        } else {
            throw IllegalArgumentException(
                "Index " + index
                        + " is out of bounds."
            )
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    fun setSelection(selectedIndices: IntArray) {
        for (i in mSelection!!.indices) {
            mSelection!![i] = false
            mSelectionAtStart!![i] = false
        }
        for (index in selectedIndices) {
            if (index >= 0 && index < mSelection!!.size) {
                mSelection!![index] = true
                mSelectionAtStart!![index] = true
            } else {
                throw IllegalArgumentException(
                    "Index " + index
                            + " is out of bounds."
                )
            }
        }
        simple_adapter.clear()
        simple_adapter.add(buildSelectedItemString())
    }

    private fun buildSelectedItemString(): String {
        val sb = StringBuilder()
        var foundOne = false

        for (i in _items!!.indices) {
            if (mSelection!![i]) {
                if (foundOne) {
                    sb.append(", ")
                }
                foundOne = true

                sb.append(_items!![i])
            }
        }
        return sb.toString()
    }
}
