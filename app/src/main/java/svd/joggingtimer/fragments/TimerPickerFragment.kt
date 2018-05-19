package svd.joggingtimer.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker

import svd.joggingtimer.R
import svd.joggingtimer.util.from_HHMMSS_ToLong

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TimerPickerFragment.OnTimerTimeConfirmedListener] interface
 * to handle interaction events.
 * Use the [TimerPickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerPickerFragment : DialogFragment() {
    private lateinit var mTag: String
    private var mListener: OnTimerTimeConfirmedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTag = arguments!!.getString(ARG_TAG)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer_picker, container, false)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context).apply {
            setTitle("Placeholder")
            //setMessage("Hi this is the message")
            val view = activity?.layoutInflater?.inflate(R.layout.fragment_timer_picker, null)?.apply {
                findViewById<NumberPicker>(R.id.numberPickerHours).apply {
                    maxValue = 23
                    minValue = 0
                }

                findViewById<NumberPicker>(R.id.numberPickerMinutes).apply {
                    maxValue =59
                    minValue=0
                }

                findViewById<NumberPicker>(R.id.numberPickerSeconds).apply {
                    maxValue = 59
                    minValue =0
                }
            }
            setView(view)

            setPositiveButton("Confirm") { dialog, which ->
                view?.let {
                    val hh = it.findViewById<NumberPicker>(R.id.numberPickerHours)!!.value
                    val mm = it.findViewById<NumberPicker>(R.id.numberPickerMinutes)!!.value
                    val ss = it.findViewById<NumberPicker>(R.id.numberPickerSeconds)!!.value

                    mListener?.onTimeConfirmed(from_HHMMSS_ToLong(hh, mm, ss), mTag)
                }

                println("Confirm")
            }
            setNegativeButton("Cancel") { dialog, which ->
                println("Cancel")
            }
        }.create()
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnTimerTimeConfirmedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnTimerTimeConfirmedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnTimerTimeConfirmedListener {
        fun onTimeConfirmed(long: Long, tag: String)
    }

    companion object {
        private val ARG_TAG = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment TimerPickerFragment.
         */
        fun newInstance(param1: String): TimerPickerFragment {
            return TimerPickerFragment().apply {
                arguments = Bundle().apply { putString(ARG_TAG, param1) }
            }

        }
    }
}// Required empty public constructor
