package com.fr.exam.Exception;

/**
 * @author furao
 * @desc
 * @date 2021/2/21
 * @package com.fr.exam.Exception
 */
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CustomException(String message){
        super(message);
    }

    public CustomException(Throwable cause)
    {
        super(cause);
    }

    public CustomException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
