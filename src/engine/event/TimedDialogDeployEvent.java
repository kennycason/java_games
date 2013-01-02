package engine.event;

import engine.dialog.AbstractDialog;

public class TimedDialogDeployEvent extends AbstractTimedDeployableEvent {
	
	protected AbstractDialog dialog;
	
	public TimedDialogDeployEvent(AbstractDialog dialog, long deployTime) {
		super(deployTime);
		this.dialog = dialog;
	}

	@Override
	public void trigger() {
		dialog.trigger();
	}

}
