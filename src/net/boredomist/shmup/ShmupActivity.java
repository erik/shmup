package net.boredomist.shmup;

import net.boredomist.shmup.game.Difficulty;
import net.boredomist.shmup.gui.GameState;
import net.boredomist.shmup.gui.GameThread;
import net.boredomist.shmup.gui.GameView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class ShmupActivity extends Activity {
	private GameView mPanel;
	private GameThread mThread;

	@Override
	public void onBackPressed() {
		switch (mThread.getGameState()) {
		case MENU:
			(new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.STRING_QUIT)
					.setMessage(R.string.STRING_REALLY_QUIT)
					.setPositiveButton(R.string.STRING_YES,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// Stop the activity
									ShmupActivity.this.finish();

									/*
									 * Debug.stopMethodTracing();
									 */
								}

							}).setNegativeButton(R.string.STRING_NO, null))
					.show();
			break;
		default:
			mThread.endGame();
		}
		return;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * Debug.startMethodTracing("shmup");
		 */

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.shmup_layout);

		mPanel = (GameView) findViewById(R.id.game_view);
		mThread = mPanel.getThread();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.MENU_MENU:
			mThread.endGame();
			return true;
		case R.id.MENU_EASY:
			mThread.startGame(Difficulty.EASY);
			return true;
		case R.id.MENU_MEDIUM:
			mThread.startGame(Difficulty.MEDIUM);
			return true;
		case R.id.MENU_HARD:
			mThread.startGame(Difficulty.HARD);
			return true;
		case R.id.MENU_LUDICROUS:
			mThread.startGame(Difficulty.LUDICROUS);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		GameState state = mThread.getGameState();

		MenuInflater inflater = getMenuInflater();
		menu.clear();

		if (state == GameState.MENU) {
			inflater.inflate(R.menu.menu, menu);
		} else {
			inflater.inflate(R.menu.game, menu);
		}

		return true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		mThread.restoreState(state);
		mThread.run();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// just have the View's thread save its state into our Bundle
		super.onSaveInstanceState(outState);
		mThread.saveState(outState);
	}
}