package hudson.plugins.powershell;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.tasks.CommandInterpreter;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Invokes Windows PowerShell from Jenkins.
 * 
 * @author Kohsuke Kawaguchi
 */
public class PowerShell extends CommandInterpreter {
    @DataBoundConstructor
    public PowerShell(String command) {
        super(command);
    }

    protected String getFileExtension() {
        return ".ps1";
    }

    public String[] buildCommandLine(FilePath script) {
        return new String[] { "powershell.exe", "-ExecutionPolicy", "ByPass", "& \'" + script.getRemote() + "\'"};
    }

    protected String getContents() {
        return command + "\r\nexit $LastExitCode";
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        @Override
        public String getHelpFile() {
            return "/plugin/powershell/help.html";
        }
        
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        public String getDisplayName() {
            return "Windows PowerShell";
        }
    }
}
