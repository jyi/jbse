package jbse.bc;

import static jbse.common.Type.ARRAYOF;
import static jbse.common.Type.CHAR;
import static jbse.common.Type.INT;
import static jbse.common.Type.REFERENCE;
import static jbse.common.Type.TYPEEND;

/**
 * This class declares the signatures for many of the 
 * standard JRE classes, interfaces, fields and methods.
 * 
 * @author Pietro Braione
 *
 */
public final class Signatures {
    //classes and interfaces
    public static final String JAVA_CLASS               = "java/lang/Class";
    public static final String JAVA_CLONEABLE           = "java/lang/Cloneable";
    public static final String JAVA_ENUM                = "java/lang/Enum";
    public static final String JAVA_LINKEDLIST          = "java/util/LinkedList";
    public static final String JAVA_LINKEDLIST_ENTRY    = "java/util/LinkedList$Entry";
    public static final String JAVA_OBJECT              = "java/lang/Object";
    public static final String JAVA_THROWABLE           = "java/lang/Throwable";
    public static final String JAVA_SERIALIZABLE        = "java/io/Serializable";
    public static final String JAVA_STACK_TRACE_ELEMENT = "java/lang/StackTraceElement";
    public static final String JAVA_STRING              = "java/lang/String";
    public static final String JAVA_STRING_CASEINSCOMP  = "java/lang/String$CaseInsensitiveComparator";

    //exceptions
    public static final String ARITHMETIC_EXCEPTION 				= "java/lang/ArithmeticException";
    public static final String ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION 	= "java/lang/ArrayIndexOutOfBoundsException";
    public static final String CLASS_CAST_EXCEPTION 				= "java/lang/ClassCastException";
    public static final String INDEX_OUT_OF_BOUNDS_EXCEPTION 		= "java/lang/IndexOutOfBoundsException";
    public static final String NEGATIVE_ARRAY_SIZE_EXCEPTION 		= "java/lang/NegativeArraySizeException";
    public static final String NULL_POINTER_EXCEPTION				= "java/lang/NullPointerException";
    
    //errors
    public static final String ABSTRACT_METHOD_ERROR                = "java/lang/AbstractMethodError";
    public static final String ILLEGAL_ACCESS_ERROR                 = "java/lang/IllegalAccessError";
    public static final String INCOMPATIBLE_CLASS_CHANGE_ERROR		= "java/lang/IncompatibleClassChangeError";
    public static final String NO_CLASS_DEFINITION_FOUND_ERROR      = "java/lang/NoClassDefFoundError";
    public static final String NO_SUCH_FIELD_ERROR                  = "java/lang/NoSuchFieldError";
    public static final String NO_SUCH_METHOD_ERROR                 = "java/lang/NoSuchMethodError";
    public static final String VERIFY_ERROR                         = "java/lang/VerifyError";
    
    //methods
    public static final Signature JAVA_THROWABLE_FILLINSTACKTRACE =
        new Signature(JAVA_THROWABLE, "()" + REFERENCE + JAVA_THROWABLE + TYPEEND, "fillInStackTrace");
    public static final Signature JAVA_THROWABLE_GETSTACKTRACEDEPTH = 
        new Signature(JAVA_THROWABLE, "()" + INT, "getStackTraceDepth");
    public static final Signature JAVA_THROWABLE_GETSTACKTRACEELEMENT = 
        new Signature(JAVA_THROWABLE, "(" + INT + ")" + REFERENCE + JAVA_STACK_TRACE_ELEMENT + TYPEEND, "getStackTraceElement");
    
    //fields
    public static final Signature JAVA_CLASS_NAME = 
        new Signature(JAVA_CLASS, "" + REFERENCE + JAVA_STRING + TYPEEND, "name");
    public static final Signature JAVA_STRING_HASH = 
        new Signature(JAVA_STRING, "" + INT, "hash");
    public static final Signature JAVA_STRING_OFFSET = 
        new Signature(JAVA_STRING, "" + INT, "offset");
    public static final Signature JAVA_STRING_COUNT = 
        new Signature(JAVA_STRING, "" + INT, "count");
    public static final Signature JAVA_STRING_VALUE = 
        new Signature(JAVA_STRING, "" + ARRAYOF + CHAR, "value");
    public static final Signature JAVA_THROWABLE_STACKTRACE = 
        new Signature(JAVA_THROWABLE, ARRAYOF + REFERENCE + JAVA_STACK_TRACE_ELEMENT + TYPEEND, "stackTrace");

    /**
     * Do not instantiate it! 
     */
    private Signatures() {
        //intentionally empty
    }

}