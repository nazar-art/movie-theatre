package net.lelyak.edu.utils;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nazar_Lelyak
 */
public final class TestNGListener extends TestListenerAdapter implements IInvokedMethodListener, ISuiteListener {

    private static List<String> exceptions;
    private String currentMessage = "";
    private boolean firstAttmptKey = true;

    /**
     * Test class
     */
    @Override
    public void onStart(ITestContext context) {
        Logger.info(buildMessage(Logger.PREFIX_TEST_STARTED, context.getName()));
    }

    @Override
    public void onFinish(ITestContext context) {
        Logger.info(buildMessage(Logger.PREFIX_TEST_FINISHED, context.getName()));
    }

    /**
     * Test Method (Test)
     */
    @Override
    public void onTestStart(ITestResult result) {
        Logger.info(buildMessage(Logger.PREFIX_METHOD_STARTED, getMethodName(result)));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logger.info(buildMessage(Logger.PREFIX_METHOD_SUCCESS, getMethodName(result)));
        processTestResult(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logger.info(buildMessage(Logger.PREFIX_METHOD_FAILED, getMethodName(result)));
    }

    private void processFailure(ITestResult result) {
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Logger.info(buildMessage(Logger.PREFIX_METHOD_SKIPPED, getMethodName(result)));
    }

    /**
     * Configuration
     */
    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        String methodName = itr.getMethod().getMethodName();
        if (methodName != null && !exceptionsList().contains(methodName)) {
            Logger.info(buildMessage(Logger.PREFIX_CONFIGURATION_SUCCESS, getMethodName(itr)));
            processTestResult(itr);
        }
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        String methodName = itr.getMethod().getMethodName();
        if (methodName != null && !exceptionsList().contains(methodName)) {
            Logger.info(buildMessage(Logger.PREFIX_CONFIGURATION_FAILED, getMethodName(itr)));
        }
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        String methodName = itr.getMethod().getMethodName();
        if (methodName != null && !exceptionsList().contains(methodName)) {
            Logger.info(buildMessage(Logger.PREFIX_CONFIGURATION_SKIPPED, getMethodName(itr)));
        }
    }

    @Override
    public void beforeConfiguration(ITestResult tr) {
        String methodName = tr.getMethod().getMethodName();
        if (methodName != null && !exceptionsList().contains(methodName)) {
            Logger.info(buildMessage(Logger.PREFIX_CONFIGURATION_STARTED, getMethodName(tr)));
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult iTestResult) {
        String methodName = iTestResult.getMethod().getMethodName();
        if (methodName != null && !exceptionsList().contains(methodName)) {
            if (!currentMessage.equals("START TEST CASE: " + methodName)) {
                currentMessage = "START TEST CASE: " + methodName;
                Logger.debug(currentMessage);
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult iTestResult) {
        /*int result = testResult.getStatus();

        if (result == ITestResult.FAILURE && firstAttmptKey) {
            processFailure(testResult);
        }

        firstAttmptKey = !firstAttmptKey;*/

        String methodName = iTestResult.getMethod().getMethodName();
        if (methodName != null && !exceptionsList().contains(methodName)) {
            if (!currentMessage.equals("END TEST CASE: " + methodName)) {
                currentMessage = "END TEST CASE: " + methodName;
                Logger.debug(currentMessage);
            }
        }
    }

    public String buildMessage(String prefix, String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(" - ");
        builder.append(msg);
        return builder.toString();
    }

    /**
     * Get name and description of Test Method as String.
     *
     * @param result result of test.
     * @return method name.
     */
    private String getMethodName(ITestResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append(result.getTestClass().getRealClass().getSimpleName());
        builder.append(".");
        builder.append(result.getMethod().getMethodName());
        Object description = result.getMethod().getDescription();

        if (null != description) {
            builder.append(" - ");
            builder.append(description);
        }

        return builder.toString();
    }

    private void processTestResult(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            Logger.error("Following error thrown while test execution: ", throwable);
        }
    }

    @Override
    public void onStart(ISuite suite) {
        Logger.info(buildMessage(Logger.PREFIX_TEST_SUITE_STARTED, suite.getName()));
    }

    @Override
    public void onFinish(ISuite suite) {
        Logger.info(buildMessage(Logger.PREFIX_TEST_SUITE_FINISHED, suite.getName()));
    }

    private List<String> exceptionsList() {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
            exceptions.add("springTestContextPrepareTestInstance");
            exceptions.add("springTestContextBeforeTestMethod");
            exceptions.add("springTestContextBeforeTestClass");
            exceptions.add("springTestContextAfterTestMethod");
            exceptions.add("springTestContextAfterTestClass");
        }
        return exceptions;
    }

}
